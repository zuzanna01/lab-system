package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOfferDto;
import pl.edu.pw.zpoplaws.labsystem.Exception.AppException;
import pl.edu.pw.zpoplaws.labsystem.Mapper.ExamMapper;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;
import pl.edu.pw.zpoplaws.labsystem.Repository.ExamOfferRepository;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamOfferRepository examOfferRepository;
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
}
