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
import pl.edu.pw.zpoplaws.labsystem.Mapper.UserMapper;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Model.UserRole;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;

import java.nio.CharBuffer;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(String id) {
        return userRepository.findById(new ObjectId(id)).orElseThrow(
                () -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }

    @Override
    public User findById(ObjectId id) {
        var optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDto login(CredentialsDto credentials) {
        var user = userRepository.findByEmail(credentials.getEmail()).orElseThrow(
                () -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentials.getPassword()), user.getPassword())) {
            return userMapper.toDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(SignUpDto signUpDto, UserRole role) {
        var user = userRepository.findByEmail(signUpDto.getE_mail());
        if (user.isPresent()) {
            throw new AppException("User already exist", HttpStatus.BAD_REQUEST);
        }

        var userList = userRepository.findByPESEL(signUpDto.getPesel());
        for (User u : userList) {
            if (u.getUserRole() == role) {
                throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
            }
        }

        User newUser = User.builder()
                .PESEL(signUpDto.getPesel())
                .name(signUpDto.getName())
                .lastname(signUpDto.getLastname())
                .userRole(role)
                .email(signUpDto.getE_mail())
                .phoneNumber(signUpDto.getPhoneNumber())
                .password(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())))
                .isActive(true)
                .build();

        var u = userRepository.save(newUser);

        return userMapper.toDto(u);
    }

    @Override
    public UserDto inactivateUser(ObjectId userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setIsActive(false);
                    userRepository.save(user);
                    return userMapper.toDto(user);
                })
                .orElseThrow(() -> new AppException("User not found with id: " + userId, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<UserDto> getAccountsByPesel(String pesel) {
        return userRepository.findByPESEL(pesel).stream().map(UserMapper::toDto).toList();
    }


}
