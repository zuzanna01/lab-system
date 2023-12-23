package pl.edu.pw.zpoplaws.labsystem.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class AppointmentMapper {

    private final DateTimeFormatter formatter;

    public AppointmentDto toDto(Appointment appointment) {

        return AppointmentDto.builder().
                id(appointment.getId().toString()).
                status(appointment.getStatus().toString()).
                date(appointment.getDateTime().toLocalDate().toString()).
                time(appointment.getDateTime().toLocalTime().toString()).
                labAddress(appointment.getLabPoint().getAddress()).
                patientName(appointment.getPatient().getName() + " " + appointment.getPatient().getLastname()).
                examName(appointment.getExamOffer().getName()).
                build();

    }
}
