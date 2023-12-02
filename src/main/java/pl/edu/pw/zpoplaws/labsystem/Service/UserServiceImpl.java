package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
}
