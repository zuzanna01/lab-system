package pl.edu.pw.zpoplaws.labsystem.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.pw.zpoplaws.labsystem.Dto.CredentialsDto;
import pl.edu.pw.zpoplaws.labsystem.Model.User;
import pl.edu.pw.zpoplaws.labsystem.Model.UserRole;
import pl.edu.pw.zpoplaws.labsystem.Repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class AuthorizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        var user = User.builder().
                name("Anna").
                lastname("Kowalska").
                PESEL("01300307781").
                isActive(true).
                userRole(UserRole.ROLE_PATIENT).
                email("ania@gmail.com").
                phoneNumber("345 678 222").
                password(passwordEncoder.encode("password")).
                build();

        userRepository.save(user);
    }
    @Test
    public void testLoginEndpoint() throws Exception {
        CredentialsDto credentials = new CredentialsDto("ania@gmail.com", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentials)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().exists("access_token"))
                .andExpect(MockMvcResultMatchers.cookie().exists("refresh_token"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Anna Kowalska"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userRole").value("ROLE_PATIENT"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        CredentialsDto credentials = new CredentialsDto("ania2@gmail.com", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentials)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testLoginIncorrectPassword() throws Exception {
        CredentialsDto credentials = new CredentialsDto("ania@gmail.com", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentials)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
