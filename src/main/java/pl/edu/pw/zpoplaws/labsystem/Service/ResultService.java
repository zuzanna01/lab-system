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
    Result findResultById(ObjectId objectId);

    Result createResult(Appointment appointment, User employee);

    Result uploadResult(ObjectId resultId, String xml, User employee);

    byte[] getResultPdf(ObjectId patientId,ObjectId id);

    Page<ResultPatientInfo> getResultsByPatient(ObjectId patientId, Pageable pageable);

    Page<ResultDto> getAllWaitingResultsByLab(ObjectId labId, Pageable pageable);
}
