package Devmaster_Lesson09.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private Integer quantity;
    private Double price;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)  // Khóa ngoại trỏ tới Author
    Author author;
}
