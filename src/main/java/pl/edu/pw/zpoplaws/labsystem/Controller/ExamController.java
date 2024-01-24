package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.NewExamOfferRequest;
import pl.edu.pw.zpoplaws.labsystem.Model.Exam;
import pl.edu.pw.zpoplaws.labsystem.Service.ExamService;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
@AllArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping("")
    public ResponseEntity<List<ExamDto>> getAllExams(){
        return ResponseEntity.ok(examService.getAllExams());
    }
    @PostMapping("/offer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExamOfferDto> addExamOffer(NewExamOfferRequest newExamOfferRequest){
        return ResponseEntity.ok(ExamOfferDto.builder().build());
    }

    @GetMapping("/offer")
    public ResponseEntity<Page<ExamOfferDto>> getExamOffer(Pageable pageable) {
        return ResponseEntity.ok().body(examService.getExamOffersWithPagination(pageable));
    }

}
