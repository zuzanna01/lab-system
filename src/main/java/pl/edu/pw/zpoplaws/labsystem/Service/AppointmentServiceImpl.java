package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Dto.AppointmentDto;
import pl.edu.pw.zpoplaws.labsystem.Exception.AppException;
import pl.edu.pw.zpoplaws.labsystem.Mapper.AppointmentMapper;
import pl.edu.pw.zpoplaws.labsystem.Model.Appointment;
import pl.edu.pw.zpoplaws.labsystem.Model.ExamOffer;
import pl.edu.pw.zpoplaws.labsystem.Model.LabPoint;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Repository.AppointmentRepository;
import pl.edu.pw.zpoplaws.labsystem.Repository.LabPointRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final LabPointRepository labPointRepository;

    private static final int parallelAppointments = 2;
    private static final Duration duration = Duration.ofMinutes(20);


    @Override
    public boolean createAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId) {
        List<Appointment> appointments = new ArrayList<>();
        var labPoint = labPointRepository.findById(labPointId);
        LocalDate currentDate = startDate;
        if (labPoint.isPresent()) {
            var openingTime = labPoint.get().getOpeningTime();
            var closingTime = labPoint.get().getClosingTime();
            while (currentDate.isBefore(endDate)) {
                appointments.addAll(this.generateAppointmentsForDay(openingTime, closingTime, labPoint.get(), currentDate));
                currentDate = currentDate.plusDays(1);
            }
        }
        return !appointmentRepository.saveAll(appointments).isEmpty();
    }

    @Override
    public AppointmentDto cancelAppointment(Appointment appointment) {
        var now =LocalDateTime.now().minusMinutes(5);
        if(appointment.getDateTime().isBefore(now)){
            appointment.cancel();
        } else {
            appointment.makeAvailable();
        }
        var a =appointmentRepository.save(appointment);
        return appointmentMapper.toDto(a);
    }

    @Override
    public boolean completeAppointment(ObjectId appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            var appointment = appointmentOptional.get();

            try {
                appointment.complete();
                appointmentRepository.save(appointment);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private List<Appointment> generateAppointmentsForDay(LocalTime startTime, LocalTime endTime, LabPoint labPoint, LocalDate day) {
        List<Appointment> appointments = new ArrayList<>();
        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            LocalTime appointmentStartTime = currentTime;
            for (int i = 1; i <= parallelAppointments; i++) {
                Appointment appointment = Appointment.builder().
                        labPoint(labPoint).
                        counter(i).
                        status(Appointment.Status.AVAILABLE).
                        dateTime(LocalDateTime.of(day, currentTime)).
                        build();
                appointments.add(appointment);
            }
            currentTime = appointmentStartTime.plus(duration);
        }
        return appointments;
    }

    @Override
    public Map<String, Set<String>> getAvailableAppointments(LocalDate startDate, LocalDate endDate, ObjectId labPointId) {
        Map<String, Set<String>> timeTable = new TreeMap<>();
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.now());
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
        var availableAppointments = appointmentRepository.
                findAvailableAppointmentsByLabPointAndDateTimeBetween(labPointId, start, end);
        for (var appointment : availableAppointments) {
            LocalDate date = appointment.getDateTime().toLocalDate();
            String dateString = date.toString();
            timeTable.putIfAbsent(dateString, new TreeSet<>());
            timeTable.get(dateString).add(appointment.getDateTime().toLocalTime().toString());
        }

        return timeTable;
    }

    @Override
    public List<LabPoint> getAllLabPoints() {
        return labPointRepository.findAll();
    }

    @Override
    public Appointment makeAppointment(Appointment appointment, User patient, ExamOffer exam) {
        appointment.reserve(exam, patient);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Override
    public Appointment findAvailableAppointment(ObjectId LabPointId, LocalDateTime localDateTime) {
        return appointmentRepository.findAvailableAppointmentsByDateTimeAndLabPoint(LabPointId, localDateTime).
                stream().findFirst().orElseThrow(() -> new AppException("No available appointment found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Appointment findAppointment(ObjectId objectId) {
        var optionalAppointment = appointmentRepository.findById(objectId);
        return optionalAppointment.orElseThrow(() -> new AppException("Unknown appointment", HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<Appointment> getTodayAppointmentsByLabPoint(ObjectId labPointId, Pageable pageable) {
        var today = LocalDate.now();
        var start = LocalDateTime.of(today, LocalTime.of(0, 0, 1));
        var end = LocalDateTime.of(today, LocalTime.of(23, 59, 59));
        return appointmentRepository.findReservedAppointmentsByDateTimeAndLabPoint(labPointId, start, end, pageable);
    }

    @Override
    public Page<Appointment> getFutureAppointmentsByPatient(ObjectId patientId, Pageable pageable) {
        var now = LocalDateTime.now();
        return appointmentRepository.findFutureReservedAppointmentsByPatient(patientId, now, pageable);
    }


}
