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
       /* User patient = User.builder()
                .PESEL("01310201559")
                .password(passwordEncoder.encode("password"))
                .name("Beata")
                .lastname("Słonko")
                .e_mail("promycze123@gmail.com")
                .phoneNumber("873245610")
                .userRole(UserRole.ROLE_PATIENT)
                .build();

        User admin = User.builder()
                .PESEL("96040701509")
                .password(passwordEncoder.encode("password"))
                .name("Mateusz")
                .lastname("Poniatowski")
                .e_mail("promycze123@gmail.com")
                .phoneNumber("123456789")
                .userRole(UserRole.ROLE_ADMIN)
                .build();

        User employee = User.builder()
                .PESEL("98110301443")
                .password(passwordEncoder.encode("password"))
                .name("Aleksandra")
                .lastname("Kropelka")
                .e_mail("alex@gmail.com")
                .phoneNumber("987654321")
                .userRole(UserRole.ROLE_EMPLOYEE)
                .build();

        userRepository.saveAll(List.of(patient,admin,employee));

        */
    }
}