package pl.edu.pw.zpoplaws.labsystem.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Result;


@Mapper
public interface ResultMapper {
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);
    ResultDto resultToResultDto(Result entity);
}