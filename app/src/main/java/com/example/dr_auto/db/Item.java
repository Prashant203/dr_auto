package com.example.dr_auto.db;

public class Item {

    private String grName;
    private String address;


    public Item() {
    }

    public Item(String grName, String address) {
        this.address = address;
        this.grName = grName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrName() {
        return grName;
    }

    public void setGrName(String grName) {
        this.grName = grName;
    }
}