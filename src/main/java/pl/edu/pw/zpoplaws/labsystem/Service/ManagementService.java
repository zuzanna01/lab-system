package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;

import java.time.LocalDateTime;

public interface ManagementService {

    AppointmentDto bookAppointment(ObjectId patientId, ObjectId examId, ObjectId labPoint, LocalDateTime localDateTime);

    ResultDto createResultOrder(ObjectId employeeId, ObjectId appointmentId);

    ResultDto uploadResult(ObjectId resultId, String xmlFile, ObjectId employeeId);

}
