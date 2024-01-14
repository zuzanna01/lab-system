package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDto {

    String id;
    String ICD9code;
    String name;
}
