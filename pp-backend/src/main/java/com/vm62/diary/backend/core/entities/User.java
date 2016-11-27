package com.vm62.diary.backend.core.entities;

import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.common.password.PasswordEncoded;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER")
public class User implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 10)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 45)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 45)
    private String lastName;

    @Column(name = "PASSWORD", nullable = false, length = 45)
    private String password;

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

    @Column(name = "REGISTRATION_ID", unique = false, nullable = false, length = 45)
    private String registrationId;

    @Column(name = "REGISTRATION_APPROVED", unique = false, nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean isRegister;

    @Column(name = "IS_MAIL_SENT", columnDefinition = "BIT", length = 1)
    private Boolean isMailSent;




    public User() {
        //default constructor
    }

    public User(String firstName, String lastName, PasswordEncoded password, Gender gender, String studyGroup,
                Date birthDate, String email, String registrationId, Boolean isRegister, Boolean isMailSent) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password.getAsString();
        this.gender = gender;
        this.studyGroup = studyGroup;
        this.birthDate = birthDate;
        this.email = email;
        this.registrationId = registrationId;
        this.isRegister = isRegister;
        this.isMailSent = isMailSent;
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

    public String getRegistrationId() {
        return registrationId;
    }

    public Boolean isRegister() {
        return isRegister;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public void setRegister(Boolean register) {
        isRegister = register;
    }

    @Transient
    public PasswordEncoded getPassword() {
        return new PasswordEncoded(password);
    }

    @Transient
    public void setPasswordEncoded(PasswordEncoded password) {
        this.password = password.getAsString();
    }

    public Boolean getMailSent() {
        return isMailSent;
    }

    public void setMailSent(Boolean mailSent) {
        isMailSent = mailSent;
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
