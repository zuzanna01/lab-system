package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;

import java.util.Optional;

public interface ExamOfferRepository extends MongoRepository<ExamOffer, ObjectId> {
    Optional<ExamOffer> getExamOfferByName(String name);
}
