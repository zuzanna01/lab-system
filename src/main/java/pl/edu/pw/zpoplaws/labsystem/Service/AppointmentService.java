package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import pl.edu.pw.zpoplaws.labsystem.Model.LabPoint;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AppointmentService {

    boolean createAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId);

    Map<String, Set<String>> getAvailableAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId);

    List<LabPoint> getAllLabPoints();
}
