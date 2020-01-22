package com.AUC.mob_apps_project.Model;

public class UsersClass {

    public String fullname;
    public String email;
    public String phone;
    public String auth;

    public UsersClass(){

    }

    public UsersClass(String fullname, String email, String phone, String auth) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.auth = auth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
