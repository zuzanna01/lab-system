package pl.edu.pw.zpoplaws.labsystem.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.w3c.dom.Document;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;

import java.util.List;

public interface ResultService {
    boolean saveToDatabase(String xml);

    byte[] getResult(String id);

    boolean validateXmlFile(String xml);

    String getXmlString(String id);

    byte[] convertToPdf(String html);

    String convertToHtml(String xml);

    Page<Result> getAllResultsByUser(String patientId, Pageable pageable);

    String getPeselFromXml(String xml) ;

}
