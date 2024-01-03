package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultPatientInfo {
    String id;
    String examName;
    String lastChangeTime;
    String status;
}
