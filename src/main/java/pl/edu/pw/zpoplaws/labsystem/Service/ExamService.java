package pl.edu.pw.zpoplaws.labsystem.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;

public interface ExamService {

    Page<ExamOfferDto> getExamOffersWithPagination(Pageable pageable);
}
