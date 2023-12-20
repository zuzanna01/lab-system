package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "labPoints")
public class LabPoint {

    @Id
    ObjectId id;
    String address;
    LocalTime openingTime;
    LocalTime closingTime;

}
