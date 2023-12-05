package pl.edu.pw.zpoplaws.labsystem.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDto {
    private String email;
    private String password;
}
