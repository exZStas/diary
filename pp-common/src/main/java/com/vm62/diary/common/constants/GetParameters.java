package com.vm62.diary.common.constants;

public enum GetParameters {
    REGISTRATION_ID("REGISTRATION_ID")

    ;

    String parameter;
    GetParameters(String parameter){
        this.parameter = parameter;
    }

    public String getParameter(){
        return this.parameter;
    }
}
