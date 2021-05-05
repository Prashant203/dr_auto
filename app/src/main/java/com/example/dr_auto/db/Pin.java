package com.example.dr_auto.db;

public class Pin {

    String pin;
    String city;

    public Pin() {
    }

    public Pin(String pin, String subAdminArea) {
        this.pin = pin;
        this.city = subAdminArea;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSubAdminArea() {
        return city;
    }

    public void setSubAdminArea(String subAdminArea) {
        this.city = subAdminArea;
    }
}
