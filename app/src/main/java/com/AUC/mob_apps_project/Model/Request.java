package com.AUC.mob_apps_project.Model;

import java.util.List;

public class Request {

    public String phone, restaurant, total, fullname;
    List<Order> foods;
    public Request(){

    }

    public Request(String phone, String fullname, String restaurant, String total, List<Order> foods) {
        this.total = total;
        this.fullname=fullname;
        this.restaurant = restaurant;
        this.phone = phone;
        this.foods = foods;
    }
}
