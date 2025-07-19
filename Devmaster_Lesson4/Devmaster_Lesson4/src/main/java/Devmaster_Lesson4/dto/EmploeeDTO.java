package Devmaster_Lesson4.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploeeDTO {
    @NotBlank(message = "Fullname cannot blank")
    @Size(min = 3,max = 25,message = "Full name tu 3 den 25 ky tu")
    String fullName;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age must be less than or equal to 60")
    int age;

    @NotBlank(message = "Gender cannot be blank")
    String gender;

    @DecimalMin(value = "1.0", message = "Salary must be greater than 0")
    float salary;

}
