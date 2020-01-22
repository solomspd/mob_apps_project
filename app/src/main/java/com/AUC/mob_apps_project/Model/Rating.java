package com.AUC.mob_apps_project.Model;

public class Rating {

    private String fullname;
    private String restaurant;
    private String value;
    private String comment;

    public Rating(String fullname, String restaurant, String value, String comment) {
        this.fullname = fullname;
        this.restaurant = restaurant;
        this.value = value;
        this.comment = comment;
    }

    public Rating() {

    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
