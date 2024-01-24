package pl.edu.pw.zpoplaws.labsystem.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
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
import pl.edu.pw.zpoplaws.labsystem.Model.User;
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
        var user = userService.login(credentials);
        var accessCookie = createCookie("access_token",user.getId(),2);
        var refreshCookie = createCookie("refresh_token",user.getId(),10);
        servletResponse.addCookie(accessCookie);
        servletResponse.addCookie(refreshCookie);

        var response = UserDetailsResponse
                .builder().userName(user.getName() + " " + user.getLastname())
                .userRole(user.getRole()).build();

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

    private Cookie createCookie(String name, String userId, int time){
        var now = LocalDateTime.now();
        ZoneOffset zoneOffset = OffsetDateTime.now().getOffset ();
        var startValidity = now.toInstant(zoneOffset);
        var accessValidity = now.plusMinutes(time).toInstant(zoneOffset);
        var token = userAuthProvider.createToken(userId,startValidity,accessValidity);
        return userAuthProvider.createCookie(name,token,time*60-5);
    }

    @PostMapping("/register/patient")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        var user =  userService.register(signUpDto, UserRole.ROLE_PATIENT);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/employee")
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
        ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
        var now = LocalDateTime.now();

        // Set expiration time in the past for invalidating the cookies
        var expirationTime = now.minusDays(1).atOffset(zoneOffset);

        // Invalidate access_token cookie
        Cookie accessCookie = new Cookie("access_token", null);
        accessCookie.setMaxAge(5); // Set the cookie to expire immediately
        accessCookie.setPath("/"); // Set the cookie's path
        accessCookie.setHttpOnly(true); // Set HttpOnly flag if required
        accessCookie.setSecure(true); // Set Secure flag if required
        servletResponse.addCookie(accessCookie);

        // Invalidate refresh_token cookie
        Cookie refreshCookie = new Cookie("refresh_token", null);
        refreshCookie.setMaxAge(5); // Set the cookie to expire immediately
        refreshCookie.setPath("/"); // Set the cookie's path
        refreshCookie.setHttpOnly(true); // Set HttpOnly flag if required
        refreshCookie.setSecure(true); // Set Secure flag if required
        servletResponse.addCookie(refreshCookie);
    }

    @PutMapping("/password")
    public ResponseEntity<Boolean> changePassword(@CookieValue(name="access_token") String token, String newPassword) {
        var id = userAuthProvider.getID(token);
        return ResponseEntity.ok(userService.changePassword(new ObjectId(id),newPassword));
    }

}
