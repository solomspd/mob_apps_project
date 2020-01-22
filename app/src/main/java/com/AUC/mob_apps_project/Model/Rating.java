package com.AUC.mob_apps_project.Model;

public class Rating {

    private String phone;
    private String restaurant;
    private String value;
    private String comment;

    public Rating(String phone, String restaurant, String value, String comment) {
        this.phone = phone;
        this.restaurant = restaurant;
        this.value = value;
        this.comment = comment;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
