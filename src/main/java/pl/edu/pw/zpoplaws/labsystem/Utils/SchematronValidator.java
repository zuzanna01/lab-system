package pl.edu.pw.zpoplaws.labsystem.Utils;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import pl.edu.pw.zpoplaws.labsystem.Repository.ResultRepository;
import pl.edu.pw.zpoplaws.labsystem.Service.ResultService;
import pl.edu.pw.zpoplaws.labsystem.Service.ResultServiceImpl;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;

@Component
public class SchematronValidator {
    /*
    public static void main(String[] args) {
        String schematronFilePath = "src/main/resources/plcda-runtime-plCdaLabReport/plcda-plCdaLabReport.sch";
        String xmlToValidate = "src/main/resources/laboratoryResults/result3.xml";


        try {
            // Load Schematron schema from file
            ISchematronResource schematronResource = SchematronResourcePure.fromFile(new File(schematronFilePath));
            // Validate XML against Schematron
            SchematronOutputType output = schematronResource.applySchematronValidationToSVRL(
                    new StreamSource(new File(xmlToValidate)));

            // Check validation result
            if (output != null && output.getActivePatternAndFiredRuleAndFailedAssert().isEmpty()) {
                System.out.println("XML is valid according to the Schematron schema.");
            } else {
                System.out.println("XML is NOT valid according to the Schematron schema.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public static void main(String[] args) {
        String xmlFilePath = "C:\\Users\\User\\Desktop\\lab-system\\src\\main\\resources\\laboratoryResults\\result1.xml";
        String xsltFilePath = "C:\\Users\\User\\Desktop\\lab-system\\src\\main\\resources\\Transformaty_XSLT_CDA_PL_IG_1.3.1.2\\CDA_PL_IG_1.3.1.xsl";
        String outputFilePath = "C:\\Users\\User\\Desktop\\lab-system\\src\\main\\result1.html";

        try {
            // Create TransformerFactory and load XSLT stylesheet
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFilePath)));

            // Perform the transformation
            transformer.transform(
                    new StreamSource(new File(xmlFilePath)),
                    new StreamResult(new File(outputFilePath))
            );

            System.out.println("Transformation successful. Output written to " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}