package com.krish.yamaha_admin.User;

public class UserData {
    private String name,email,password,bikemodel,image,key;

    public UserData() {
    }

    public UserData(String name, String email, String password, String bikemodel, String image, String key) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bikemodel = bikemodel;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBikemodel() {
        return bikemodel;
    }

    public void setBikemodel(String bikemodel) {
        this.bikemodel = bikemodel;
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
