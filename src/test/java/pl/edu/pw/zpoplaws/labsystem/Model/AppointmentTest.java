package pl.edu.pw.zpoplaws.labsystem.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentTest {

    @Test
    void reserve() {
        //given
        var labPoint = LabPoint.builder().address("testAdress").build();
        var appointment =Appointment.builder().dateTime(LocalDateTime.now())
                .status(Appointment.Status.AVAILABLE).labPoint(labPoint).build();
        var examOffer = ExamOffer.builder().name("testExam").build();
        var patient =User.builder().name("testName").build();
        //when
        appointment.reserve(examOffer,patient);
        //then
        assertEquals(Appointment.Status.RESERVED, appointment.getStatus());
        assertEquals(patient, appointment.getPatient());
        assertEquals(examOffer,appointment.getExamOffer());
    }
}