package com.krish.yamaha_admin.Stock;

public class StockData {
    private String name,quntity,price,date,key;

    public StockData() {
    }

    public StockData(String name, String quntity, String price, String date, String key) {
        this.name = name;
        this.quntity = quntity;
        this.price = price;
        this.date = date;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
