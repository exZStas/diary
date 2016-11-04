package com.vm62.diary.backend.core.entities;

import com.vm62.diary.common.constants.Gender;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 10)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 45)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 45)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = true, length = 1)
    private Gender gender;

    @Column(name = "STUDY_GROUP", nullable = false, length = 10)
    private String studyGroup;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY", nullable = true)
    private Date birthDate;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 255)
    private String email;


    public User() {
        //default constructor
    }

    public User(String firstName, String lastName, Gender gender, String studyGroup,
                Date birthDate, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studyGroup = studyGroup;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public Long getId (){
        return id;
    }

    public void setId (Long Id){
        this.id = Id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
