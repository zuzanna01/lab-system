package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.Data;

@Data
public class SignUpDto {
    String pesel;
    String password;
    String name;
    String lastname;
    String e_mail;
    String phoneNumber;
}
