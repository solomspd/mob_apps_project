package com.AUC.mob_apps_project.Model;

public class Category {

    public String Name, Image;
    public long Price;
    public Category(){

    }

    public Category(String Name, String Image,Long Price) {
        this.Image = Image;
        this.Name = Name;
        this.Price = Price;
    }
}
