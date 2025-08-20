package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Type;
import java.util.List;

@Entity
@Table(name = "Configurations")
@Builder

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

    // Quan hệ với ProductConfig
    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL)
    private List<Product_Config> productConfigs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
