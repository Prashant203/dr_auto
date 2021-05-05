package com.example.dr_auto.db;

import org.jetbrains.annotations.NotNull;

public class UserAddress {

    String subLocality;
    String locality;
    String subAdminArea;
    String adminArea;
    String zip;


    public UserAddress() {
    }

    public UserAddress(String subLocality, String locality, String subAdminArea, String adminArea, String zip) {
        this.locality = locality;
        this.subLocality = subLocality;
        this.subAdminArea = subAdminArea;
        this.adminArea = adminArea;
        this.zip = zip;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }

    public String getSubAdminArea() {
        return subAdminArea;
    }

    public void setSubAdminArea(String subAdminArea) {
        this.subAdminArea = subAdminArea;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @NotNull
    @Override
    public String toString() {
        String fullAdd;
        if (subLocality != null) {
            if (!locality.equals(subAdminArea)) {
                fullAdd = subLocality + ", " + locality + ", " + subLocality + ", \n" + adminArea + ", " + zip;
            } else {
                fullAdd = subLocality + ", " + subAdminArea + ", \n" + adminArea + ", " + zip;
            }
        } else {
            fullAdd = locality + ", " + subAdminArea + ", \n" + adminArea + ", " + zip;
        }
        return fullAdd;
    }
}
