package Devmaster_Lesson4.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhoaDTO {
    @NotBlank(message = "Ma khoa cannot blank")
    @Size(min = 2,message = "Ma khoa toi thieu 2 ky tu")
    String maKh;
    @NotBlank(message = "Ma khoa cannot blank")
    @Size(min = 2,message = "Ma khoa toi thieu 2 ky tu")
    String tenKh;

}
