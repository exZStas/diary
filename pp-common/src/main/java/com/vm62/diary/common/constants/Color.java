package com.vm62.diary.common.constants;

import java.io.Serializable;

/**
 * Created by Ира on 16.12.2016.
 */
public enum Color implements Serializable {
    education("#b388ff"),
    sport("#64ffda"),
    work("#bcaaa4"),
    entertainment("#ef9a9a"),
    sleep("#1e88e5"),
    eating("#ffb300"),
    homework("#fff9c4"),
    household("#aed581"),
    trip("#ba68c8"),
    other("#bdbdbd");


    String color;
    Color(String color){this.color = color;}

    public String getColor() {
        return this.color;
    }
}
