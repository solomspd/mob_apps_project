package com.AUC.mob_apps_project.Model;

public class UsersClass {

    public String fullname, email, phone,auth;
    public UsersClass(){

    }

    public UsersClass(String fullname, String email, String phone, String auth) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.auth = auth;
    }
}
