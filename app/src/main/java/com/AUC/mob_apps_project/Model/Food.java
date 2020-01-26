package com.AUC.mob_apps_project.Model;

public class Food {

    public String name, image, description,price,discount,menuId;
    public Food(){

    }

    public Food(String name, String image,String description, String price, String discount, String menuId) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.menuId = menuId;
    }
}


