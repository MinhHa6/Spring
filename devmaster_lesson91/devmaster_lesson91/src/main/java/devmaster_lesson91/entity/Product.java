package devmaster_lesson91.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imgUrl;
    private Integer quantity;
    private Double price;
    private Boolean isActive;
    @ManyToMany
    @JoinTable(name = "Product_Config",
    joinColumns = @JoinColumn(name = "productId"),
    inverseJoinColumns = @JoinColumn(name = "configurationId"))

    private List<Configuration> configurations= new ArrayList<>();
}
