package pl.edu.pw.zpoplaws.labsystem.Service;

import org.bson.types.ObjectId;
import pl.edu.pw.zpoplaws.labsystem.Dto.CredentialsDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.SignUpDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

import java.util.Optional;

public interface UserService {

    User findById(String id);

    User findById(ObjectId objectId);

    UserDto login(CredentialsDto credentialsDto);

    UserDto register(SignUpDto signUpDto);
}
