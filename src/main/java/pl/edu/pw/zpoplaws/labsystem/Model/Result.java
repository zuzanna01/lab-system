package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "results")
public class Result {

    @Id
    Object id;
    @DBRef
    User patient;
    @DBRef
    User employee;
    @DBRef
    ExamOffer exam;
    LocalDateTime creationTime;
    LocalDateTime uploadTime;
    String xmlFile;
    Status status;

    public enum Status {
        WAITING,
        READY,
        CANCELLED
    }

    public static Result createResultOrder(Appointment appointment) {
        return Result.builder().
                status(Status.WAITING).
                creationTime(LocalDateTime.now()).
                patient(appointment.getPatient()).
                exam(appointment.getExamOffer()).
                build();
    }

    public void uploadResult(String xmlFile, User employee) {
        this.status = Status.READY;
        this.employee = employee;
        this.uploadTime = LocalDateTime.now();
        this.xmlFile = xmlFile;
    }
}
