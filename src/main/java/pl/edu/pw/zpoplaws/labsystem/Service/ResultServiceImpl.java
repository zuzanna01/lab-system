package pl.edu.pw.zpoplaws.labsystem.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;
import pl.edu.pw.zpoplaws.labsystem.Repository.ResultRepository;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import static pl.edu.pw.zpoplaws.labsystem.Utils.ResourceUtils.getResourceAsString;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    String xsltFilePath = "C:\\Users\\User\\Desktop\\lab-system\\src\\main\\resources\\Transformaty_XSLT_CDA_PL_IG_1.3.1.2\\CDA_PL_IG_1.3.1.xsl";
    String enrichment = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet href=\"CDA_PL_IG_1.3.1.xsl\" type=\"text/xsl\"?>\n" +
            "<ClinicalDocument xmlns=\"urn:hl7-org:v3\" xmlns:extPL=\"http://www.csioz.gov.pl/xsd/extPL/r2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"extPL:ClinicalDocument\">\n";

    @Override
    public boolean saveToDatabase(String xml) {
        if (validateXmlFile(xml)) {
            resultRepository.save(Result.builder().xmlFile(xml).build());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public byte[] getResult(String id) {
        var result = getXmlString(id);
        var html = convertToHtml(result);
        return convertToPdf(html);
    }

    @Override
    public byte[] getTest(){
       var result = getResourceAsString("laboratoryResults\\result1.xml");
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
             return enrichment + result.get().getXmlFile();
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
            builder.useFont(new File(ResultServiceImpl.class.getClassLoader().getResource("SourceSans3-Regular.ttf").getFile()),"Noto sans");
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

}

