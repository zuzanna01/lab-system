package pl.edu.pw.zpoplaws.labsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.pw.zpoplaws.labsystem.Dto.LabPointDto;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "labPoints")
public class LabPoint {

    @Id
    ObjectId id;
    String address;
    LocalTime openingTime;
    LocalTime closingTime;
    String phoneNumber;

    public LabPointDto toDto(){
        return LabPointDto.builder().
                id(this.id.toString()).
                address(this.address).
                openingTime(this.openingTime.toString()).
                closingTime(this.closingTime.toString()).
                phoneNumber(this.phoneNumber).
                build();
    }

}
