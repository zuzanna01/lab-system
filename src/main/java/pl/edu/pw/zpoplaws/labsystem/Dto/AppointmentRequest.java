package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AppointmentRequest {

        private List<String> labs;
        private String startDate;
        private String endDate;


}
