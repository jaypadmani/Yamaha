package com.krish.yamaha_admin.Parts;

public class PartsData {
    private String name,price,image,key;

    public PartsData() {
    }

    public PartsData(String name, String price, String image, String key) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.key = key;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
