package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;
import pl.edu.pw.zpoplaws.labsystem.Model.LabPoint;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AppointmentService {

    boolean createAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId);

    Map<String, Set<String>> getAvailableAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId);

    List<LabPoint> getAllLabPoints();

   // LabPoint findLabPointById(ObjectId objectId);
    Appointment makeAppointment(Appointment appointment, User patient, ExamOffer exam);

    Appointment makeAppointment(ObjectId patientId, ObjectId examId, ObjectId labPoint, LocalDateTime localDateTime);

    Appointment findAppointment( ObjectId LabPointId, LocalDateTime localDateTime);

    Appointment findAppointment(ObjectId objectId);
}
