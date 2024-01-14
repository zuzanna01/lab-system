package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.UserDto;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthProvider;

    @GetMapping("/details")
    public ResponseEntity<User> getDetails(@CookieValue(name="access_token") String authToken) {
        String id = userAuthProvider.getID(authToken);
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/inactivate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> inactivateAccount(@PathVariable String id) {
        var body = userService.inactivateUser(new ObjectId(id));
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{pesel}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> findAccounts(@PathVariable String pesel) {
        var body = userService.getAccountsByPesel(pesel);
        return ResponseEntity.ok(body);
    }

}
