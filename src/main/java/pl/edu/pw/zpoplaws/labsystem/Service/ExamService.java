package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.NewExamOfferRequest;
import pl.edu.pw.zpoplaws.labsystem.Model.Exam;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;

import java.util.List;

public interface ExamService {
    List<ExamDto> getAllExams();

    Page<ExamOfferDto> getExamOffersWithPagination(Pageable pageable);

    ExamOffer findExamOfferById(ObjectId objectId);

    ExamOffer createNewExamOffer(NewExamOfferRequest request);
}
