package pl.edu.pw.zpoplaws.labsystem.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class ResultMapper {

    private final DateTimeFormatter formatter;
    public ResultDto toDto (Result result) {
        return ResultDto.builder().
                id(result.getId().toString()).
                examName(result.getExam().getName()).
                employeeName(result.getEmployee().getUserName()).
                patientName(result.getPatient().getUserName()).
                creationTime(formatter.format(result.getCreationTime())).
                uploadTime(formatter.format(result.getUploadTime())).
                status(result.getStatus().toString()).
                build();
    }
}
