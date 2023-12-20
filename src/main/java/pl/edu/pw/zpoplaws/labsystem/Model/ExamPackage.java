package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Value
@AllArgsConstructor
@Document(collection = "examPackages")
public class ExamPackage {

    @Id
    ObjectId id;
    String name;
    @DBRef
    List<ExamOffer> examOffers;
    double price;
    double basePrice;

}
