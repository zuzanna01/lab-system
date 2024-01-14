package pl.edu.pw.zpoplaws.labsystem.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultPatientInfo;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ResultMapper {

    private final DateTimeFormatter formatter;
    public ResultDto toDto(Result result) {
        String examName = Optional.ofNullable(result.getExamOffer())
                .map(exam -> exam.getName())
                .orElse(null);

        String patientName = Optional.ofNullable(result.getPatient())
                .map(patient -> patient.getUserName())
                .orElse(null);

        String uploadEmployeeName = Optional.ofNullable(result.getUploadEmployee())
                .map(employee -> employee.getUserName())
                .orElse(null);

        String createEmployeeName = Optional.ofNullable(result.getCreateEmployee())
                .map(employee -> employee.getUserName())
                .orElse(null);

        String creationTime = Optional.ofNullable(result.getCreationTime())
                .map(time -> formatter.format(time))
                .orElse(null);

        String uploadTime = Optional.ofNullable(result.getUploadTime())
                .map(time -> formatter.format(time))
                .orElse(null);

        String labAddress = Optional.ofNullable(result.getUploadTime())
                .map(time -> formatter.format(time))
                .orElse(null);

        return ResultDto.builder()
                .id(result.getId().toString())
                .examName(examName)
                .labAddress(labAddress)
                .creationEmployeeName(createEmployeeName)
                .uploadEmployeeName(uploadEmployeeName)
                .patientName(patientName)
                .creationTime(creationTime)
                .uploadTime(uploadTime)
                .status(result.getStatus().toString())
                .build();
    }

    public ResultPatientInfo toPatientInfo(Result result) {

        if (result == null) {
            return null;
        }

        ResultPatientInfo resultPatientInfo = new ResultPatientInfo();
        resultPatientInfo.setId(result.getId() != null ? result.getId().toString() : null);
        resultPatientInfo.setExamName(result.getExamOffer() != null ? result.getExamOffer().getName() : null);
        resultPatientInfo.setLastChangeTime(getLastChangeTime(result));
        resultPatientInfo.setStatus(translateStatus(result.getStatus()));

        return resultPatientInfo;
    }

    private static String getLastChangeTime(Result result) {
        if (result.getUploadTime() != null) {
            return formatDateTime(result.getUploadTime());
        } else if (result.getCreationTime() != null) {
            return formatDateTime(result.getCreationTime());
        } else {
            return null;
        }
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", new Locale("pl", "PL"));
        return dateTime.format(formatter);
    }

    private static String translateStatus(Result.Status status) {
        if (status == null) {
            return null;
        }

        switch (status) {
            case WAITING:
                return "OCZEKUJĄCY";
            case READY:
                return "GOTOWY";
            case CANCELLED:
                return "ANULOWANY";
            default:
                return null;
        }
    }

}
