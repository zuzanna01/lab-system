package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "results")
public class Result {
    @Id
    String id;
    String patientPESEL;
    String xmlFile;


}
