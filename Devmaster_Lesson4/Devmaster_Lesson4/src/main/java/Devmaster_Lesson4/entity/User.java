package Devmaster_Lesson4.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Getter@Setter
public class User {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
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
