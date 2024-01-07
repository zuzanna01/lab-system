package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Service.ExamService;

@RestController
@RequestMapping("api/exam")
@AllArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping("/offer")
    public ResponseEntity<Page<ExamOfferDto>> getResult(Pageable pageable) {
        return ResponseEntity.ok().body(examService.getExamOffersWithPagination(pageable));
    }


}
