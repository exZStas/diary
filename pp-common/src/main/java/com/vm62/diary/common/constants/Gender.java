package com.vm62.diary.common.constants;

import java.io.Serializable;

public enum Gender implements Serializable {
    M ("Man"),
    W ("Woman");

    String sex;
    Gender(String sex){
        this.sex = sex;
    }
}
