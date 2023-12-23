package pl.edu.pw.zpoplaws.labsystem.Mapper;

import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;

import java.util.Locale;

@Component
public class ExamMapper {

    public ExamOfferDto toDto(ExamOffer examOffer) {
        String formattedPrice = String.format(Locale.ENGLISH, "%.2f", examOffer.getPrice());
        
        return ExamOfferDto.builder().
                id(examOffer.getId().toString()).
                name(examOffer.getName()).
                price(formattedPrice).
                description(examOffer.getDescription()).
                additionalInformation(examOffer.getAdditionalInformation()).
                requirements(examOffer.getRequirements()).
                examsCodes(examOffer.getExams().stream().map(exam -> exam.getICD9code()).toList()).
                build();
    }
}