package com.vm62.diary.frontend.server.service.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable{

    private String firstName;
    private String lastName;
    private String gender;
    private String studyGroup;
    private Date birthday;
    private String email;

    public UserDTO (String firstName, String lastName, String gender, String studyGroup, Date birthday, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studyGroup = studyGroup;
        this.birthday = birthday;
        this.email = email;
    }

    public UserDTO(){}
}
