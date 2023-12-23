package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
@Builder
public class ExamOfferDto {
    String id;
    String name;
    List<String> examsCodes;
    String additionalInformation;
    String description;
    String requirements;
    String price;
}
