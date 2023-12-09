package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;

import java.util.Optional;

public interface ResultRepository extends MongoRepository<Result, ObjectId> {

    Optional<Result> findById(ObjectId id);

    Page<Result> findByPatientId(String patientId, Pageable pageable);

}
