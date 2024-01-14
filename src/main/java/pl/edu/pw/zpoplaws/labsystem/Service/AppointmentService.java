package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
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
    Appointment findAppointment(ObjectId objectId);

    List<LabPoint> getAllLabPoints();

    Map<String, Set<String>> getAvailableAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId);

    Appointment findAvailableAppointment(ObjectId LabPointId, LocalDateTime localDateTime);

    Page<Appointment> getTodayAppointmentsByLabPoint(ObjectId labPointId, Pageable pageable);

    Page<Appointment> getFutureAppointmentsByPatient(ObjectId patientId, Pageable pageable);

    boolean createAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId);

    boolean completeAppointment(ObjectId appointmentId);

    AppointmentDto cancelAppointment(Appointment appointment);

    Appointment makeAppointment(Appointment appointment, User patient, ExamOffer exam);
}
