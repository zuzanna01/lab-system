package pl.edu.pw.zpoplaws.labsystem.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.zpoplaws.labsystem.Model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}
