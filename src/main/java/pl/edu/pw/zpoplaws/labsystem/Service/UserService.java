package pl.edu.pw.zpoplaws.labsystem.Service;

import pl.edu.pw.zpoplaws.labsystem.Dto.CredentialsDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.SignUpDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

public interface UserService {

    User findById(String id);

    UserDto login(CredentialsDto credentialsDto);

    UserDto register(SignUpDto signUpDto);
}
