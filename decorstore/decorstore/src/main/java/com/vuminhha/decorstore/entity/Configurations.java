package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Type;

@Entity
@Table(name = "Configurations")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Configurations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @Column(length = 500)
    private String name;

     @Column(columnDefinition = "TEXT")
    private String notes;
     @Column(columnDefinition = "TINYINT",nullable = false)
    private Boolean isDelete =false;
     @Column(columnDefinition = "TINYINT",nullable = false)
    private Boolean isActive =true;

}
