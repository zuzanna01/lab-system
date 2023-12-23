package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamPackage;

import java.util.Optional;

public interface ExamPackageRepository extends MongoRepository<ExamPackage, ObjectId> {

    Optional<ExamPackage> getExamPackageByName(String name);
}
