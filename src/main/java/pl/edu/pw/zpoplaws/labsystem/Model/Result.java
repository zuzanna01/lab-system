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
@Document(collection = "results")
public class Result {

    @Id
    ObjectId id;
    @DBRef
    User patient;
    @DBRef
    ExamOffer exam;
    @DBRef
    User uploadEmployee;
    @DBRef
    User createEmployee;
    @DBRef
    LabPoint labPoint;
    LocalDateTime creationTime;
    LocalDateTime uploadTime;
    String xmlFile;
    Status status;

    public enum Status {
        WAITING,
        READY,
        CANCELLED
    }

    public static Result createResultOrder(Appointment appointment, User employee) {
        return Result.builder().
                status(Status.WAITING).
                creationTime(LocalDateTime.now()).
                createEmployee(employee).
                patient(appointment.getPatient()).
                exam(appointment.getExamOffer()).
                labPoint(appointment.getLabPoint()).
                build();
    }

    public void uploadResult(String xmlFile, User employee) {
        this.status = Status.READY;
        this.uploadEmployee = employee;
        this.uploadTime = LocalDateTime.now();
        this.xmlFile = xmlFile;
    }
}
