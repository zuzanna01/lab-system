package pl.edu.pw.zpoplaws.labsystem.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@AllArgsConstructor
public class AppointmentMapper {

    private final DateTimeFormatter formatter;

    public AppointmentDto toDto(Appointment appointment) {
        AppointmentDto.AppointmentDtoBuilder dtoBuilder = AppointmentDto.builder()
                .id(Objects.nonNull(appointment.getId()) ? appointment.getId().toString() : null)
                .status(Objects.nonNull(appointment.getStatus()) ? appointment.getStatus().toString() : null)
                .date(Objects.nonNull(appointment.getDateTime()) ? appointment.getDateTime().toLocalDate().toString() : null)
                .time(Objects.nonNull(appointment.getDateTime()) ? appointment.getDateTime().toLocalTime().toString() : null)
                .labAddress(Objects.nonNull(appointment.getLabPoint()) && Objects.nonNull(appointment.getLabPoint().getAddress()) ? appointment.getLabPoint().getAddress() : null)
                .examName(Objects.nonNull(appointment.getExamOffer()) && Objects.nonNull(appointment.getExamOffer().getName()) ? appointment.getExamOffer().getName() : null);

        if (Objects.nonNull(appointment.getPatient())) {
            String patientName = Objects.nonNull(appointment.getPatient().getName()) ? appointment.getPatient().getName() : "";
            String patientLastName = Objects.nonNull(appointment.getPatient().getLastname()) ? appointment.getPatient().getLastname() : "";
            dtoBuilder.patientName(patientName + " " + patientLastName);
        } else {
            dtoBuilder.patientName(null);
        }

        return dtoBuilder.build();
    }
}
