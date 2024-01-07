package pl.edu.pw.zpoplaws.labsystem.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.CredentialsDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.SignUpDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDetailsResponse;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Model.UserRole;
import pl.edu.pw.zpoplaws.labsystem.Service.UserService;

import java.io.IOException;
import java.time.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthProvider;

    @PostMapping("/login")
    public void login(@RequestBody CredentialsDto credentials, HttpServletResponse servletResponse) {
        var now = LocalDateTime.now();
        ZoneOffset zoneOffset = OffsetDateTime.now().getOffset ();
        var startValidity = now.toInstant(zoneOffset);
        var accessValidity = now.plusMinutes(60).toInstant(zoneOffset);
        var refreshValidity = now.plusMinutes(70).toInstant(zoneOffset);
        var user = userService.login(credentials);
        var accessToken = userAuthProvider.createToken(user.getId(),startValidity,accessValidity);
        var refreshToken = userAuthProvider.createToken(user.getId(),startValidity,refreshValidity);
        var accessCookie = userAuthProvider.createCookie("access_token", accessToken,60*60-5);
        var refreshCookie = userAuthProvider.createCookie("refresh_token",refreshToken,70*60-5);
        refreshCookie.setPath("/auth/refresh");
        servletResponse.addCookie(accessCookie);
        servletResponse.addCookie(refreshCookie);

        var response = UserDetailsResponse
                .builder()
                .userName(user.getName() + " " + user.getLastname())
                .userRole(user.getRole())
                .build();

        servletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(response);
            servletResponse.setStatus(HttpServletResponse.SC_OK);
            servletResponse.getWriter().write(userJson);
        } catch (IOException e) {
            e.printStackTrace();
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/patient")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        var user =  userService.register(signUpDto, UserRole.ROLE_PATIENT);
        return ResponseEntity.ok(user);
    }

    @PostMapping("register/employee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto>registerEmployee(@RequestBody SignUpDto signUpDto) {
        var user =  userService.register(signUpDto, UserRole.ROLE_EMPLOYEE);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh")
    public void refresh(@CookieValue(name="refresh_token") String oldRefreshToken, HttpServletResponse servletResponse) {
        var id = userAuthProvider.getID(oldRefreshToken);
        var now = LocalDateTime.now();
        OffsetDateTime odt = OffsetDateTime.now ();
        ZoneOffset zoneOffset = odt.getOffset ();
        var startValidity = now.toInstant(zoneOffset);
        var accessValidity = now.plusMinutes(3).toInstant(zoneOffset);
        var refreshValidity = now.plusMinutes(5).toInstant(zoneOffset);
        var accessToken = userAuthProvider.createToken(id,startValidity,accessValidity);
        var refreshToken = userAuthProvider.createToken(id,startValidity,refreshValidity);
        var accessCookie = userAuthProvider.createCookie("access_token", accessToken,3*60-5);
        var refreshCookie = userAuthProvider.createCookie("refresh_token",refreshToken,5*60-5);
        servletResponse.addCookie(accessCookie);
        servletResponse.addCookie(refreshCookie);
    }

    @PostMapping("/logout")
    public void logout(@CookieValue(name="access_token") String token, HttpServletResponse servletResponse){
        ZoneOffset zoneOffset = OffsetDateTime.now().getOffset ();
        var now = LocalDateTime.now();
        var startValidity = now.toInstant(zoneOffset);
        var endAccessValidity = now.minusDays(1).toInstant(zoneOffset);
        var endRefreshValidity = now.minusDays(1).toInstant(zoneOffset);
        var id = userAuthProvider.getID(token);
        var accessToken = userAuthProvider.createToken(id,startValidity,endAccessValidity);
        var refreshToken = userAuthProvider.createToken(id,startValidity,endRefreshValidity);
        var accessCookie = userAuthProvider.createCookie("access_token", accessToken,1);
        var refreshCookie = userAuthProvider.createCookie("refresh_token",refreshToken,1);
        servletResponse.addCookie(accessCookie);
        servletResponse.addCookie(refreshCookie);
    }
}
