package pl.edu.pw.zpoplaws.labsystem.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Model.User;


@Component
@AllArgsConstructor
public class UserMapper {

    public static UserDto toDto (User user){
        return UserDto.builder().name(user.getName()).lastname(user.getLastname())
                .e_mail(user.getEmail()).phoneNumber(user.getPhoneNumber()).id(user.getId().toString())
                .role(user.getUserRole().name()).isActive(user.getIsActive()).build();
    }

}

