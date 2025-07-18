package Devmaster_Lesson4.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Khoa {
    String maKh;
    String tenKh;
}
