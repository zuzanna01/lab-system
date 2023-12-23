package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {

    @Id
    ObjectId id;
    @DBRef
    LabPoint labPoint;
    LocalDateTime dateTime;
    int counter;
    Status status;
    @DBRef
    ExamOrder examOrder;
    @DBRef
    User patient;

    public enum Status {
        AVAILABLE,
        RESERVED,
        COMPLETED,
        CANCELLED
    }

    public boolean isAvailable(){
        return status == Status.AVAILABLE;
    }
}
