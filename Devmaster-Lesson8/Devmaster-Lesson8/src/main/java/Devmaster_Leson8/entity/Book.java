package Devmaster_Leson8.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private Integer quantity;
    private Double price;
    private Boolean isActive;
    // tao moi quan he voi bang author
    @ManyToMany
    @JoinTable (
            name="Book-Author",
    joinColumns = @JoinColumn(name = "bookId"),
    inverseJoinColumns = @JoinColumn(name = "authorId"))
    private List<Author> authors= new ArrayList<>();

}
