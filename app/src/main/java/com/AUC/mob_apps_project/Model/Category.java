package com.AUC.mob_apps_project.Model;

public class Category {

    public String Name, Image,Category;
    public long Price;
    public Category(){

    }

    public Category(String Name, String Image,Long Price,String Category) {
        this.Image = Image;
        this.Name = Name;
        this.Price = Price;
        this.Category = Category;
    }
}
