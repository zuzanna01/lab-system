package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    String id;
    String name;
    String lastname;
    String e_mail;
    String phoneNumber;
    String role;
    boolean isActive;

}
