package pl.edu.pw.zpoplaws.labsystem.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDto {
    String id;
    String examName;
    String labAddress;
    String uploadTime;
    String creationTime;
    String patientName;
    String uploadEmployeeName;
    String creationEmployeeName;
    String status;
}
