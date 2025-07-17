package Devmaster_Lesson4.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import jakarta.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityScan
@Getter@Setter
public class User {
    @Id;

    Long id;
    String userName;
    String passWord;
    String fullName;
    LocalDate birthDay;
    String email;
    String phone;
    int age;
    Boolean status;
}
