package com.AUC.mob_apps_project.Model;

public class RestaurantClass {
    public String city;
    public String image;
    public String description;
    public double latitude;
    public double longitude;
    public String name;
    public String price_range;





    public RestaurantClass(){

    }

    public RestaurantClass(String city, String image, String description, double longitude, double latitude,String name, String price_range) {
        this.city = city;
        this.image = image;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price_range = price_range;

    }
}
