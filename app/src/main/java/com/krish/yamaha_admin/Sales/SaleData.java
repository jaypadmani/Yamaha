package com.krish.yamaha_admin.Sales;

public class SaleData {
    private String name,number,price,aadharnumber,image,key;

    public SaleData() {
    }

    public SaleData(String name, String number, String price, String aadharnumber, String image, String key) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.aadharnumber = aadharnumber;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAadharnumber() {
        return aadharnumber;
    }

    public void setAadharnumber(String aadharnumber) {
        this.aadharnumber = aadharnumber;
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
