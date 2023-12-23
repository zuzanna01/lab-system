package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findById(ObjectId id);

    Optional<User> findByPESEL(String PESEL);

    Optional<User> findByEmail(String email);
}
