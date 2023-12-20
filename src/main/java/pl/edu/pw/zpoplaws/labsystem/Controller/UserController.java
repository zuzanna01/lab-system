package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.SignUpDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@PathVariable SignUpDto signUpDto) {
        var user = userService.register(signUpDto);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/details")
    public ResponseEntity<User> getDetails(@CookieValue(name="access_token") String authToken) {
        String id = userAuthProvider.getID(authToken);
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/name")
    public ResponseEntity<String> getName(@RequestHeader("Authorization") String authToken) {
        String id = userAuthProvider.getID(authToken.substring(7));
        var user = userService.findById(id);
        return ResponseEntity.ok(user.getName()+" "+user.getLastname());
    }

}
