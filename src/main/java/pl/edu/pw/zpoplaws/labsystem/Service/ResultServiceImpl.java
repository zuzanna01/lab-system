package pl.edu.pw.zpoplaws.labsystem.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Repository.ResultRepository;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    String xsltFilePath = "C:\\Users\\User\\Desktop\\lab-system\\src\\main\\resources\\Transformaty_XSLT_CDA_PL_IG_1.3.1.2\\CDA_PL_IG_1.3.1.xsl";
    String enrichment = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet href=\"CDA_PL_IG_1.3.1.xsl\" type=\"text/xsl\"?>\n" +
            "<ClinicalDocument xmlns=\"urn:hl7-org:v3\" xmlns:extPL=\"http://www.csioz.gov.pl/xsd/extPL/r2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"extPL:ClinicalDocument\">";

    @Override
    public boolean saveToDatabase(String xml) {
        if (validateXmlFile(xml)) {
            String fullxml = enrich(xml);
            var id = getPeselFromXml(fullxml);
            resultRepository.save(Result.builder().xmlFile(fullxml).patientId(id).uploadTime(LocalDateTime.now()).build());
            return true;
        } else {
            return false;
        }
    }

    private String enrich (String xml) {
        String modifiedXml = xml.replaceAll("\\\\r\\\\n","\n").replaceAll("\\\\\"", "\"");
        int indexOfNewLine = modifiedXml.indexOf("\n");
        if (indexOfNewLine != -1) {
            String stringWithoutFirstLine = modifiedXml.substring(indexOfNewLine + 1, modifiedXml.length()-2);
            return enrichment + stringWithoutFirstLine;
        } else {
            System.out.println("No newline character found in the string.");
            return null;
        }
    }
    @Override
    public String getPeselFromXml(String xmlData) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
            doc.getDocumentElement().normalize();

            NodeList clinicalDocumentList = doc.getElementsByTagName("ClinicalDocument");
            for (int i = 0; i < clinicalDocumentList.getLength(); i++) {
                Node clinicalDocumentNode = clinicalDocumentList.item(i);
                if (clinicalDocumentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element clinicalDocumentElement = (Element) clinicalDocumentNode;
                    NodeList recordTargetList = clinicalDocumentElement.getElementsByTagName("recordTarget");
                    for (int j = 0; j < recordTargetList.getLength(); j++) {
                        Node recordTargetNode = recordTargetList.item(j);
                        if (recordTargetNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element recordTargetElement = (Element) recordTargetNode;
                            NodeList patientRoleList = recordTargetElement.getElementsByTagName("patientRole");
                            for (int k = 0; k < patientRoleList.getLength(); k++) {
                                Node patientRoleNode = patientRoleList.item(k);
                                if (patientRoleNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element patientRoleElement = (Element) patientRoleNode;
                                    NodeList idList = patientRoleElement.getElementsByTagName("id");
                                    for (int l = 0; l < idList.getLength(); l++) {
                                        Node idNode = idList.item(l);
                                        if (idNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element idElement = (Element) idNode;
                                            String rootValue = idElement.getAttribute("root");
                                            if (rootValue.equals("2.16.840.1.113883.3.4424.1.1.616")) {
                                                return idElement.getAttribute("extension");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public byte[] getResult(String id) {
        var result = getXmlString(id);
        var html = convertToHtml(result);
        return convertToPdf(html);
    }

    @Override
    public boolean validateXmlFile(String xml) {
        return true;
    }

    @Override
    public String getXmlString(String id) {
        var result = resultRepository.findById(new ObjectId(id));
        if (result.isPresent()) {
             return  result.get().getXmlFile();
        } else {
            return null;
        }
    }

    @Override
    public byte[] convertToPdf(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.html);

        byte[] pdf ;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
            builder.useFont(new File(ResultServiceImpl.class.getClassLoader().getResource("font/SourceSans3-Regular.ttf").getFile()),"Noto sans");
            builder.toStream(os);
            builder.run();
            pdf = os.toByteArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pdf;
    }

    @Override
    public String convertToHtml(String xml) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltFilePath));
            StringWriter writer = new StringWriter();
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(writer));
            String htmlResult = writer.toString();
            return htmlResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ResultDto> getAllResultsByUser(String patientId, Pageable pageable) {
        User user = userRepository.findById(new ObjectId(patientId)).get();
        return resultRepository.findByPatientId(user.getPESEL(), pageable)
                .map(this::toResultDto);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    public ResultDto toResultDto (Result result) {

        var time = formatter.format(result.getUploadTime());
        return  ResultDto.builder().id(result.getId()).resultName(result.getResultName()).uploadTime(time).build();
    }

}

