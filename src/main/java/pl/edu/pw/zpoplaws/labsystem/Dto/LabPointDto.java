package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LabPointDto {

    private String id;
    private String address;
    private String openingTime;
    private String closingTime;

}
