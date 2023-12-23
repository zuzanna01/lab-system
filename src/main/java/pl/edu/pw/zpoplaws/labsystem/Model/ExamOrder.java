package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "examOrders")
public class ExamOrder {

    @Id
    ObjectId id;
    String patientId;
    String resultName;
    LocalDateTime uploadTime;
    String xmlFile;

}
