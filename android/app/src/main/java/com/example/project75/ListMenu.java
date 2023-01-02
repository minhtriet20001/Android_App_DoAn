package com.example.project75;

public class ListMenu {
    String image;
    String name;
    String price;
    String total;


    public ListMenu(String image, String name, String price, String total) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.total = total;
    }

    public ListMenu(String name, String price, String image) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
