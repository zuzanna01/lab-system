package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;

public interface ExamService {
    Page<ExamOfferDto> getExamOffersWithPagination(Pageable pageable);

    ExamOffer findExamOfferById(ObjectId objectId);
}
