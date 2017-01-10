package com.vm62.diary.backend.core.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 11)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 45)
    private String categoryName;

    @Column(name = "COLOR", nullable = false, length = 45)
    private String categoryColor;

    public Category(String categoryName, String categoryColor){
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public Category(){}

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }
}
