package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Mapper.AppointmentMapper;
import pl.edu.pw.zpoplaws.labsystem.Mapper.ResultMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService {

    private final AppointmentService appointmentService;
    private final UserService userService;
    private final ExamService examService;
    private final ResultService resultService;

    private final AppointmentMapper appointmentMapper;
    private final ResultMapper resultMapper;

    @Override
    public AppointmentDto bookAppointment(ObjectId patientId, ObjectId examId, ObjectId labPoint, LocalDateTime localDateTime) {
        var patient = userService.findById(patientId);
        var appointment = appointmentService.findAppointment(labPoint, localDateTime);
        var exam = examService.findExamOfferById(examId);
        var updatedAppointment = appointmentService.makeAppointment(appointment,patient,exam);
        return appointmentMapper.toDto(updatedAppointment);
    }

    @Override
    public ResultDto createResultOrder(ObjectId employeeId, ObjectId appointmentId) {
        var employee = userService.findById(employeeId);
        var appointment = appointmentService.findAppointment(appointmentId);
        var result = resultService.createResultOrder(appointment);
        return resultMapper.toDto(result);
    }

    @Override
    public ResultDto uploadResult(ObjectId resultId, String xmlFile, ObjectId employeeId) {
        var user = userService.findById(employeeId);
        var result = resultService.uploadResult(resultId, xmlFile, user);
        return resultMapper.toDto(result);
    }
}
