package com.AUC.mob_apps_project.Model;

public class Order {

    public String ProductId, ProductName,Quantity,Price,Discount;
    public Order(){

    }

    public Order(String productId, String productName,String quantity, String price, String discount) {
        this.ProductId = productId;
        this.ProductName = productName;
        this.Quantity = quantity;
        this.Price = price;
        this.Discount = discount;
    }

    public String getProductId() {
        return ProductId;
    }
    public String getQuantity() {
        return Quantity;
    }

}


