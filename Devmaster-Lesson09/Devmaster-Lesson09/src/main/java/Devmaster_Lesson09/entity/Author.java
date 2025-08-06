package Devmaster_Lesson09.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "authors")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private String email;
    private String phone;
    private String address;
    private boolean isActive;
}
