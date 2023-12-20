package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.zpoplaws.labsystem.Model.Exam;

import java.util.Optional;

public interface ExamRepository extends MongoRepository<Exam, ObjectId> {

    Optional<Exam> getExamByICD9code(String ICD9code);
}
