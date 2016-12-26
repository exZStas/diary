package com.vm62.diary.backend.core.entities;

import com.vm62.diary.common.password.PasswordEncoded;

import javax.persistence.*;

@Entity
@Table(name = "ADMIN")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 11)
    private Long id;

    @Column(name = "ADMIN_NAME", nullable = false, length = 45)
    private String adminName;

    @Column(name = "ADMIN_PASSWORD", nullable = false, length = 45)
    private String password;

    public Long getId() {
        return id;
    }

    public String getAdminName() {
        return adminName;
    }

    @Transient
    public PasswordEncoded getPassword() {
        return new PasswordEncoded(password);
    }

}
