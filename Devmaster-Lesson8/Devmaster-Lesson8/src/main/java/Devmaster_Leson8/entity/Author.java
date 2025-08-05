package Devmaster_Leson8.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    //tao moi quan he voi bang book
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
}
