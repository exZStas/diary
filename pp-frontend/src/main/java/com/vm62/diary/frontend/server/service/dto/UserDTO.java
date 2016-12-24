package com.vm62.diary.frontend.server.service.dto;

import com.vm62.diary.common.password.Password;
import com.vm62.diary.common.password.PasswordEncoded;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable{

    private String firstName;
    private String lastName;
    private String gender;
    private String studyGroup;
    private Date birthday;
    private String email;
    private String password;

    public UserDTO (String firstName, String lastName, String gender, String studyGroup, Date birthday, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studyGroup = studyGroup;
        this.birthday = birthday;
        this.email = email;
    }

    public UserDTO (String firstName, String lastName, String gender, String studyGroup, Date birthday, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studyGroup = studyGroup;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
    }

    public UserDTO(){}

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

}
