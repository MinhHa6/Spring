package Devmaster_Lesson4.entity;

import jakarta.persistence.*;
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
    Long Id;
    String userName;
    String passWord;
    String fullName;
    LocalDate birthDay;
    String email;
    String phone;
    int age;
    Boolean status;

}
