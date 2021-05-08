package com.example.dr_auto.db;

import org.jetbrains.annotations.NotNull;

public class Item {

    private int garageId;
    private String name;
    private String street;
    private String area;
    private String landmark;
    private int pincode;
    private long contact;
    private String format;

    public Item() {
    }

    public Item(int garageId, String name, String street, String area, String landmark, int pincode, long contact, String format) {
        this.garageId = garageId;
        this.name = name;
        this.street = street;
        this.area = area;
        this.landmark = landmark;
        this.pincode = pincode;
        this.contact = contact;
        this.format = format;
    }

    public int getGarageId() {
        return garageId;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @NotNull
    @Override
    public String toString() {
        return "Item{" +
                "garageId=" + garageId +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", area='" + area + '\'' +
                ", landmark='" + landmark + '\'' +
                ", pincode=" + pincode +
                ", contact=" + contact +
                ", distance=" + format +
                '}';
    }
}