package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "exams")
public class Exam {

    @Id
    ObjectId id;
    String ICD9code;
    String name;

}
