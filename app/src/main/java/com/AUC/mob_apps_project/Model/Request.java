package com.AUC.mob_apps_project.Model;

import java.util.List;

public class Request {

    private String phone;
    private String restaurant;
    private String total;
    private String fullname;
    private List<Order> foods;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request(){

    }

    public Request(String phone, String restaurant, String total, String fullname, List<Order> foods,String status) {
        this.phone = phone;
        this.restaurant = restaurant;
        this.total = total;
        this.fullname = fullname;
        this.foods = foods;
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
