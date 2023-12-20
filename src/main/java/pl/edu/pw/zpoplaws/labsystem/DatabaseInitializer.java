package pl.edu.pw.zpoplaws.labsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Model.*;
import pl.edu.pw.zpoplaws.labsystem.Repository.ExamOfferRepository;
import pl.edu.pw.zpoplaws.labsystem.Repository.ExamPackageRepository;
import pl.edu.pw.zpoplaws.labsystem.Repository.ExamRepository;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExamRepository examRepository;
    private final ExamOfferRepository examOfferRepository;
    private final ExamPackageRepository examPackageRepository;

    @Override
    public void run(String... args) throws Exception {
  /*  User patient = User.builder()
                .PESEL("62091599999")
                .password(passwordEncoder.encode("password"))
                .name("Jan Franciszek")
                .lastname("Kowalski")
                .email("kowal@gmail.com")
                .phoneNumber("667456889")
                .userRole(UserRole.ROLE_PATIENT)
                .isActive(true)
                .build();

        User admin = User.builder()
                .PESEL("96040701509")
                .password(passwordEncoder.encode("password"))
                .name("Mateusz")
                .lastname("Poniatowski")
                .email("promycze123@gmail.com")
                .phoneNumber("123456789")
                .userRole(UserRole.ROLE_ADMIN)
                .isActive(true)
                .build();

        User employee = User.builder()
                .PESEL("98110301443")
                .password(passwordEncoder.encode("password"))
                .name("Aleksandra")
                .lastname("Kropelka")
                .email("alex@gmail.com")
                .phoneNumber("987654321")
                .userRole(UserRole.ROLE_EMPLOYEE)
                .isActive(true)
                .build();

        userRepository.saveAll(List.of(patient,admin,employee));
*/ /*
        Exam exam1 = Exam.builder().
                ICD9code("N 45").name("Potas w surowicy").build();
        Exam exam2 = Exam.builder().
                ICD9code("O 35").name("Sód w surowicy").build();
        Exam exam3 = Exam.builder().
                ICD9code("C 55").name("Morfologia pełna").build();

        examRepository.saveAll(List.of(exam1,exam2,exam3));

        ExamOffer examOffer1 = ExamOffer.builder().
                exams(List.of(examRepository.getExamByICD9code("N 45").get(), examRepository.getExamByICD9code("O 35").get())).
                name("Mikroelementy: sód i potas").additionalInformation("z krwii").
                description("Sód i potas badamy głównie w celu sprawdzenia równowagi wodno-elektrolitowej, " +
                        "w której ważną rolę odgrywają nerki, serce i wątroba.").
                requirements("Na czczo (13-14 h), o 7.00-10.00, ostatni posiłek poprzedniego dnia o 18.00.").
                price(12.00).
                build();

        ExamOffer examOffer2 = ExamOffer.builder().
                exams(List.of(examRepository.getExamByICD9code("C 55").get())).
                name("Morfologia pełna").
                additionalInformation("(rozmaz z analizatora) CBC +DIFF (krew pełna EDTA)").
                price(12.00).
                description("Polega na ilościowej i jakościowej ocenie elementów morfotycznych, czyli komórek krwi. ").
                requirements("Na czczo (13-14 h), o 7.00-10.00, ostatni posiłek poprzedniego dnia o 18.00.").
                build();
        examOfferRepository.saveAll(List.of(examOffer1,examOffer2));

        ExamPackage examPackage = ExamPackage.builder().
                examOffers(List.of(examOfferRepository.getExamOfferByName("Morfologia pełna").get(),
                        examOfferRepository.getExamOfferByName("Mikroelementy: sód i potas").get())).
                price(22.00).
                basePrice(24.00).name("Morfologia + Mikroelementy").build();



        examPackageRepository.saveAll(List.of(examPackage));*/
    }
}