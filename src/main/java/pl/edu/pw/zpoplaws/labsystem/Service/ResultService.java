package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultPatientInfo;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

public interface ResultService {
    Result createResultOrder(Appointment appointment, User employee);
    Result uploadResult(ObjectId resultId, String xml, User employee);

    boolean validateXmlFile(String xml);

    byte[] convertToPdf(String html);

    String convertToHtml(String xml);

    byte[] getResultPdf(ObjectId id);

    Page<ResultPatientInfo> getResultsByPatient(ObjectId patientId, Pageable pageable);

    Page<ResultDto> getAllWaitingResultsByLab(ObjectId labId, Pageable pageable);

    Result findResultById(ObjectId objectId);

}
