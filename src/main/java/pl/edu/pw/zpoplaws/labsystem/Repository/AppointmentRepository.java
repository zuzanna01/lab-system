package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, ObjectId> {


    @Query("{'labPoint.id': ?0, 'dateTime': {$gte: ?1, $lte: ?2}, 'status': 'AVAILABLE'}")
    List<Appointment> findAvailableAppointmentsByLabPointAndDateTimeBetween(
            ObjectId labPointId, LocalDateTime startDate, LocalDateTime endDate);


}
