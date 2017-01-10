package com.vm62.diary.common.constants;

import java.io.Serializable;

/**
 * Created by Ира on 16.12.2016.
 */
public enum Category implements Serializable{

    education ("Education", "#b388ff"),
    sport("Sport","#64ffda"),
    work("Work","#bcaaa4"),
    entertainment("Entertainment","#ef9a9a"),
    sleep("Sleep","#1e88e5"),
    eating("Eating","#ffb300"),
    classes("Classes", "#a1887f"),
    homework("Homework","#fff9c4"),
    household("Household chores","#aed581"),
    trip("Trip","#ba68c8"),
    other("Other","#bdbdbd");

    String category;
    String color;
    Category(String category, String color){
        this.category = category;
        this.color = color;
    }

    public String getCategory() {
        return category;
    }
    public String getColor(){
        return color;
    }

}
