package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.NewExamOfferRequest;
import pl.edu.pw.zpoplaws.labsystem.Exception.AppException;
import pl.edu.pw.zpoplaws.labsystem.Mapper.ExamMapper;
import pl.edu.pw.zpoplaws.labsystem.Model.Exam;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;
import pl.edu.pw.zpoplaws.labsystem.Repository.ExamOfferRepository;
import pl.edu.pw.zpoplaws.labsystem.Repository.ExamRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamOfferRepository examOfferRepository;
    private final ExamRepository examRepository;
    private final ExamMapper examMapper;


    @Override
    public Page<ExamOfferDto> getExamOffersWithPagination(Pageable pageable) {
       var page = examOfferRepository.findAll(pageable);
       return page.map(examMapper::toDto);
    }

    @Override
    public ExamOffer findExamOfferById(ObjectId objectId) {
        var optionalExamOffer = examOfferRepository.findById(objectId);
        return optionalExamOffer.orElseThrow(() -> new AppException("Unknown exam offer", HttpStatus.NOT_FOUND));
    }

    @Override
    public ExamOffer createNewExamOffer(NewExamOfferRequest request) {

        List<Exam> examList = new ArrayList<Exam>();
        for (String e : request.getExamsCodes()){
            var exam =examRepository.getExamByICD9code(e);
            if(exam.isPresent()){
                examList.add(exam.get());
            }
        }
        var examOffer = ExamOffer.builder().name(request.getName())
                .additionalInformation(request.getAdditionalInformation())
                .price(Double.valueOf(request.getPrice()))
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .exams(examList).build();

        return examOfferRepository.save(examOffer);
    }

    @Override
    public List<ExamDto> getAllExams() {
        return examRepository.findAll().stream().map(ExamMapper::toDto).toList();
    }
}
