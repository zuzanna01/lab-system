package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private String id;
    private String labAddress;
    private String date;
    private String time;
    private String status;
    private String examName;
    private String patientName;

}
