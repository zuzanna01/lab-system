package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    ObjectId id;
    String PESEL;
    String password;
    String name;
    String lastname;
    String e_mail;
    String phoneNumber;
    UserRole userRole;
}
