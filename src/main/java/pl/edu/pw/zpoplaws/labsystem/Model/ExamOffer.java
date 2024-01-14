package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "examOffers")
public class ExamOffer {

    @Id
    ObjectId id;
    String name;
    @DBRef
    List<Exam> exams;
    String additionalInformation;
    String description;
    String requirements;
    double price;
}
