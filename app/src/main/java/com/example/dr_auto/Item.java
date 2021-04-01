package com.example.dr_auto;

public class Item {

    private String time;
    private String grName;
    private String price;
    private String duuration;
    private String type;

    public Item(String grName, String price, String duuration, String type, String time) {
        this.grName = grName;
        this.price = price;
        this.duuration = duuration;
        this.type = type;
        this.time = time;
    }

    public String grName() {
        return grName;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {return type;}

    public String getTime() {return time;}

    public String getDuuration() {
        return duuration;
    }

    public void setName(String grName) {
        this.grName = grName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDuuration(String email) {
        this.duuration = duuration;
    }
}