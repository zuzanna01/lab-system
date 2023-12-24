package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

public interface ResultService {
    Result uploadResult(ObjectId resultId, String xml, User employee);

    boolean validateXmlFile(String xml);


    byte[] convertToPdf(String html);

    String convertToHtml(String xml);

    byte[] getResultPdf(ObjectId id);

    Result createResultOrder(Appointment appointment);

    Page<ResultDto> getAllReadyResultsByUser(ObjectId patientId, Pageable pageable);

    Page<ResultDto> getAllWaitingResultsByUser(ObjectId patientId, Pageable pageable);

    Result findResultById(ObjectId objectId);

}
