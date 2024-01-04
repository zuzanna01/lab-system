package pl.edu.pw.zpoplaws.labsystem.Controller;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentRequest;
import pl.edu.pw.zpoplaws.labsystem.Dto.LabPointDto;
import pl.edu.pw.zpoplaws.labsystem.Mapper.AppointmentMapper;
import pl.edu.pw.zpoplaws.labsystem.Model.LabPoint;
import pl.edu.pw.zpoplaws.labsystem.Service.AppointmentService;
import pl.edu.pw.zpoplaws.labsystem.Service.ManagementService;

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
    private final ManagementService managementService;
    private final DateTimeFormatter formatter;

    private final AppointmentMapper appointmentMapper;

    @GetMapping("/labs")
    public ResponseEntity<List<LabPointDto>> getLabs() {
        var labs = appointmentService.getAllLabPoints().stream().map(LabPoint::toDto).toList();
        return ResponseEntity.ok().body(labs);
    }

    @GetMapping("/available")
    public ResponseEntity<Map<String, Set<String>>> getAvailableAppointments(String startDate, String endDate, String labPointId) {
        var localStartDate = LocalDate.parse(startDate, formatter);
        var localCloseDate = LocalDate.parse(endDate, formatter);
        return ResponseEntity.ok().body(appointmentService.getAvailableAppointments(
                localStartDate, localCloseDate, new ObjectId(labPointId)));
    }

    @PutMapping("/make")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<AppointmentDto> makeAppointment(@CookieValue(name = "access_token") String token,
                                                          String examOfferId, String dateTime, String labPointId) {
        var localDateTime = LocalDateTime.parse(dateTime, formatter);
        var patient = new ObjectId(userAuthProvider.getID(token));
        var examOffer = new ObjectId(examOfferId);
        var lab = new ObjectId(labPointId);
        var body = managementService.bookAppointment(patient, examOffer, lab, localDateTime);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<String> confirmAppointment(@CookieValue(name="access_token") String authToken, String appointmentId) {
        var employeeId = new ObjectId(userAuthProvider.getID(authToken));
        var resultOrder = managementService.createResultOrder(new ObjectId(appointmentId), employeeId);
        return ResponseEntity.ok().body(resultOrder.getId());
    }

    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<AppointmentDto> cancelAppointment(@CookieValue(name="access_token") String authToken, @PathVariable String appointmentId) {
        var userId = new ObjectId(userAuthProvider.getID(authToken));
        var body = managementService.cancelAppointment(userId, new ObjectId(appointmentId));
        return ResponseEntity.ok(body);
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Page<AppointmentDto>> getTodayAppointments(String labId, Pageable pageable) {
        var body = appointmentService.getTodayAppointmentsByLabPoint(new ObjectId(labId), pageable);
        return ResponseEntity.ok().body(body.map(appointmentMapper::toDto));
    }

    @GetMapping("/future")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<Page<AppointmentDto>> getFutureAppointmentsForPatient(@CookieValue(name = "access_token") String token, Pageable pageable) {
        var patientId = new ObjectId(userAuthProvider.getID(token));
        var body = appointmentService.getFutureAppointmentsByPatient(patientId, pageable);
        return ResponseEntity.ok().body(body.map(appointmentMapper::toDto));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity createAppointments(@RequestBody AppointmentRequest request) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var start = LocalDate.parse(request.getStartDate(), formatter);
        var end = LocalDate.parse(request.getEndDate(), formatter);
        for (String labId : request.getLabs()) {
            appointmentService.createAppointments(start, end, new ObjectId(labId));
        }

        return ResponseEntity.ok().build();
    }


}
