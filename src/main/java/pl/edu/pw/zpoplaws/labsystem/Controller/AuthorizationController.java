package pl.edu.pw.zpoplaws.labsystem.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.CredentialsDto;
import pl.edu.pw.zpoplaws.labsystem.Service.UserService;

import java.io.IOException;
import java.time.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthProvider;

    @PostMapping("/login")
    public void login(@RequestBody CredentialsDto credentials, HttpServletResponse servletResponse) {

        var now = LocalDateTime.now();
        OffsetDateTime odt = OffsetDateTime.now ();
        ZoneOffset zoneOffset = odt.getOffset ();
        var startValidity = now.toInstant(zoneOffset);
        var accessValidity = now.plusMinutes(10).toInstant(zoneOffset);
        var refreshValidity = now.plusMinutes(15).toInstant(zoneOffset);
        var user = userService.login(credentials);
        var accessToken = userAuthProvider.createToken(user.getId(),startValidity,accessValidity);
        var refreshToken = userAuthProvider.createToken(user.getId(),startValidity,refreshValidity);
        var accessCookie = userAuthProvider.createCookie("access_token", accessToken,10*60-5);
        var refreshCookie = userAuthProvider.createCookie("refresh_token",refreshToken,15*60-5);
       // refreshCookie.setPath("/auth/refresh");
        servletResponse.addCookie(accessCookie);
       // servletResponse.addCookie(refreshCookie);

        servletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(user);
            servletResponse.setStatus(HttpServletResponse.SC_OK);
            servletResponse.getWriter().write(userJson);
        } catch (IOException e) {
            e.printStackTrace();
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
        //refreshCookie.setPath("/auth/refresh");
        servletResponse.addCookie(accessCookie);
        servletResponse.addCookie(refreshCookie);
    }
}
