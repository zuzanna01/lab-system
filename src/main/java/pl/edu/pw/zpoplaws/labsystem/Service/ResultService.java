package pl.edu.pw.zpoplaws.labsystem.Service;

import org.w3c.dom.Document;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;

import java.util.List;

public interface ResultService {
    boolean saveToDatabase(String xml);

    byte[] getResult(String id);

    boolean validateXmlFile(String xml);

    String getXmlString(String id);
    byte[] getTest();

    byte[] convertToPdf(String html);

    String convertToHtml(String xml);

    List<ResultDto> getAllResultsByUser();

}
