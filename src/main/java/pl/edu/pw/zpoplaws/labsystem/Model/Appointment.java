package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {

    @Id
    private ObjectId id;
    @DBRef
    private LabPoint labPoint;
    private LocalDateTime dateTime;
    private int counter;
    private Status status;
    @DBRef
    private ExamOffer examOffer;
    @DBRef
    private User patient;

    public enum Status {
        AVAILABLE,
        RESERVED,
        COMPLETED,
        CANCELLED
    }

    public boolean isAvailable(){
        return status == Status.AVAILABLE;
    }

    public void reserve(ExamOffer examOffer, User patient) {
        this.examOffer = examOffer;
        this.patient = patient;
        this.status = Status.RESERVED;
    }

    public void cancel() {
        this.status =Status.CANCELLED;
    }

    public void makeAvailable(){
        this.examOffer = null;
        this.patient = null;
        this.status = Status.AVAILABLE;
    }

    public void complete() {
        this.status = Status.COMPLETED;
    }
}
