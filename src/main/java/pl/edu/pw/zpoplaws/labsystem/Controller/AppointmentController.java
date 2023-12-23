package pl.edu.pw.zpoplaws.labsystem.Controller;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.ExamOrderDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.LabPointDto;
import pl.edu.pw.zpoplaws.labsystem.Model.LabPoint;
import pl.edu.pw.zpoplaws.labsystem.Service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController {

    private final UserAuthenticationProvider userAuthProvider;
    private final AppointmentService appointmentService;
    private final DateTimeFormatter formatter;

    @GetMapping("/labs")
    public ResponseEntity<List<LabPointDto>> getResult() {
        var labs = appointmentService.getAllLabPoints().stream().map(LabPoint::toDto).toList();
        return ResponseEntity.ok().body(labs);
    }

    @GetMapping("/available")
    public ResponseEntity<Map<String, Set<String>>> getResult(String startDate, String endDate, String labPointId) {
        var localStartDate = LocalDate.parse(startDate, formatter);
        var localCloseDate = LocalDate.parse(endDate, formatter);
        return ResponseEntity.ok().body(appointmentService.getAvailableAppointments(
                localStartDate, localCloseDate, new ObjectId(labPointId)));
    }

    @PutMapping
    public ResponseEntity<ExamOrderDto> makeAppointment(@CookieValue(name="access_token") Cookie token, String examOfferId, String dateTime){
        var localDateTime = LocalDateTime.parse(dateTime, formatter);
        return ResponseEntity.ok().body(ExamOrderDto.builder().build());
    }


}
