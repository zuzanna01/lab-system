package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Exception.AppException;
import pl.edu.pw.zpoplaws.labsystem.Mapper.AppointmentMapper;
import pl.edu.pw.zpoplaws.labsystem.Mapper.ResultMapper;
import pl.edu.pw.zpoplaws.labsystem.Model.UserRole;

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
        var appointment = appointmentService.findAvailableAppointment(labPoint, localDateTime);
        var exam = examService.findExamOfferById(examId);
        var updatedAppointment = appointmentService.makeAppointment(appointment,patient,exam);
        return appointmentMapper.toDto(updatedAppointment);
    }

    @Override
    public ResultDto createResultOrder(ObjectId appointmentId, ObjectId employeeId) {
        var appointment = appointmentService.findAppointment(appointmentId);
        appointmentService.completeAppointment(appointmentId);
        var employee = userService.findById(employeeId);
        var result = resultService.createResult(appointment, employee);
        return resultMapper.toDto(result);
    }

    @Override
    public ResultDto uploadResult(ObjectId resultId, String xmlFile, ObjectId employeeId) {
        var employee = userService.findById(employeeId);
        var result = resultService.uploadResult(resultId, xmlFile, employee);
        return resultMapper.toDto(result);
    }

    @Override
    public AppointmentDto cancelAppointment(ObjectId userId, ObjectId appointmentId) {
        var user = userService.findById(userId);
        var appointment =  appointmentService.findAppointment(appointmentId);
        if(user.getUserRole() == UserRole.ROLE_PATIENT && !appointment.getPatient().equals(user)){
           throw new AppException("It is not yout appointment", HttpStatus.UNAUTHORIZED );
        }
        return appointmentService.cancelAppointment(appointment);
    }
}
