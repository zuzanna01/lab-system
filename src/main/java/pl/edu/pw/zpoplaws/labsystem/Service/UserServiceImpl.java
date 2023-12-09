package pl.edu.pw.zpoplaws.labsystem.Service;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoplaws.labsystem.Dto.CredentialsDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.SignUpDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Exception.AppException;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Model.UserRole;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;

import java.nio.CharBuffer;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(String id) {
        return  userRepository.findById(new ObjectId(id)).orElseThrow(
                () -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDto login(CredentialsDto credentials) {
        var user = userRepository.findByEmail(credentials.getEmail()).orElseThrow(
        () -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentials.getPassword()),user.getPassword())){
            return UserDto.builder().name(user.getName()).lastname(user.getLastname())
                    .e_mail(user.getEmail()).phoneNumber(user.getPhoneNumber()).id(user.getId().toString()).build();
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(SignUpDto signUpDto) {
       var user  = userRepository.findByPESEL(signUpDto.getPESEL());

       if(user.isPresent()){
           throw new AppException("User already exist", HttpStatus.BAD_REQUEST);
       }

      user  = userRepository.findByEmail(signUpDto.getE_mail());

       if(user.isPresent()){
           throw new AppException("User already exist", HttpStatus.BAD_REQUEST);
       }

        User newUser = User.builder()
               .PESEL(signUpDto.getPESEL())
               .name(signUpDto.getName())
               .lastname(signUpDto.getLastname())
               .userRole(UserRole.ROLE_PATIENT)
               .email(signUpDto.getE_mail())
               .phoneNumber(signUpDto.getPhoneNumber())
               .password(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())))
               .build();

        var  u = userRepository.save(newUser);

        return UserDto.builder().name(u.getName()).lastname(u.getLastname())
                .e_mail(u.getEmail()).phoneNumber(u.getPhoneNumber()).id(u.getId().toString()).build();
    }


}
