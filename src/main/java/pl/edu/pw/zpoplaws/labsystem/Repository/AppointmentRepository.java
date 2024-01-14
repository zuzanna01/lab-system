package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment, ObjectId> {

    @Query("{'labPoint.id': ?0, 'dateTime': {$gte: ?1, $lte: ?2}, 'status': 'AVAILABLE'}")
    List<Appointment> findAvailableAppointmentsByLabPointAndDateTimeBetween(
            ObjectId labPointId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'labPoint' : ?0,'dateTime' : ?1, 'status' : 'AVAILABLE'}")
    List<Appointment> findAvailableAppointmentsByDateTimeAndLabPoint(ObjectId labPointId, LocalDateTime dateTime);


    @Query("{'labPoint' : ?0,'dateTime': {$gte: ?1, $lte: ?2}, 'status' : 'RESERVED'}")
    Page<Appointment> findReservedAppointmentsByDateTimeAndLabPoint(ObjectId labPointId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("{'patient' : ?0,'dateTime': {$gte: ?1}, 'status' : 'RESERVED'}")
    Page<Appointment> findFutureReservedAppointmentsByPatient(ObjectId patientId, LocalDateTime startTime, Pageable pageable);

    Optional<Appointment> findById(ObjectId appointmentId);
}
