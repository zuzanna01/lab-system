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
    String resultName;
    String uploadTime;
}
