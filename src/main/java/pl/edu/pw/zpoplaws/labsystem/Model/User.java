package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    ObjectId id;
    String name;
    String lastname;
    String e_mail;
    String phoneNumber;

}