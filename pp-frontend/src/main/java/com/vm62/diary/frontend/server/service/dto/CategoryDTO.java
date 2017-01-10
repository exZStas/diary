package com.vm62.diary.frontend.server.service.dto;

import java.io.Serializable;

/**
 * Category contain defaults categories and categories from bd
 */
public class CategoryDTO implements Serializable{

    private String name;
    private String color;

    public CategoryDTO(String name, String color){
        this.name = name;
        this.color = color;
    }

    public CategoryDTO(){}

    public String getColor(){
        return this.color;
    }

    public String getName(){
        return this.name;
    }
}
