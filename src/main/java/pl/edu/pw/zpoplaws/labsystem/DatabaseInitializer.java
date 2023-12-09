package pl.edu.pw.zpoplaws.labsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Model.UserRole;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
   User patient = User.builder()
                .PESEL("62091599999")
                .password(passwordEncoder.encode("password"))
                .name("Jan Franciszek")
                .lastname("Kowalski")
                .email("kowal@gmail.com")
                .phoneNumber("667456889")
                .userRole(UserRole.ROLE_PATIENT)
                .isActive(true)
                .build();
    /*
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
*/
        userRepository.save(patient);

    }
}