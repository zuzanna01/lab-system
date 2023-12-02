package pl.edu.pw.zpoplaws.labsystem.Service;

import org.w3c.dom.Document;

public interface ResultService {
    boolean saveToDatabase(String xml);

    byte[] getResult(String id);

    boolean validateXmlFile(String xml);

    String getXmlString(String id);
    byte[] getTest();

    byte[] convertToPdf(String html);

    String convertToHtml(String xml);

}
