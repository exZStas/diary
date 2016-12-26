package com.vm62.diary.frontend.server.service.dto;

import java.io.Serializable;

public class AdminDTO implements Serializable {

    private String adminPassword;
    private String adminName;

    public AdminDTO(){
    }

    public AdminDTO(String adminName, String adminPassword){
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    public String getAdminName(){
        return adminName;
    }

    public String getAdminPassword(){
        return adminPassword;
    }
}
