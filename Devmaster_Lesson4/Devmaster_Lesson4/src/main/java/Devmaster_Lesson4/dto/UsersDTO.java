package Devmaster_Lesson4.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.Column;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersDTO {
    @NotBlank(message = "User name can not be blank")
    @Size(min = 3, max = 20, message = "User name must be between 3 and 20 characters")
    @Column(unique = true) // đúng: "unique"
    String userName;
    @NotBlank(message = "Password cannot be bank")
    @Size(min = 8,max = 30,message = "Password must br between 3 and 20 character")
    @Pattern(regexp = "(?=.*[0-9]) (?=.*[a-zA-Z]).{8,30} ",message = "Password must contain at least one letter and one number")
    String password;
    @NotBlank(message = "Fullname can ot be blank")
    @Size(min = 2,max = 50,message = "Full name must be between 2 and 50 character")
    String fullName;

    @Past(message = "Birthday must be in the past")
    LocalDate birthDay;

    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email can not be blank")
    @Column(unique = true)
    String email;
    @Pattern(regexp = "^\\+?[0-9.()-]{7,25}$",message = "Phone number is invalid")
    @NotBlank(message = "Phone number can not be blank")
    String phone;

    @Min(value = 18,message = "Age must be at least 18")
    @Max(value = 100,message = "Age must be less than or equal")
    int age;
    @NotNull (message = "Status cannot be null")
    Boolean status;
}
