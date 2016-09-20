package com.vm62.diary.backend.core.entities;

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

    @Column(name = "MIDDLE_NAME", nullable = true, length = 45)
    private String middleName;

    @Column(name = "GENDER", nullable = true, length = 10)
    private String gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDATE", nullable = true)
    private Date birthDate;

    @Column(name = "ADDRESS", nullable = true, length = 255)
    private String address;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 255)
    private String email;


    public User() {
        //default constructor
    }

    public User( String firstName, String lastName, String middleName, String gender,
                Date birthDate, String address, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                ", middleName='" + middleName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
