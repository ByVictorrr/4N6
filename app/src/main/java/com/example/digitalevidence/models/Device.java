package com.example.digitalevidence.models;

public class Device {
    private String name, image, os, manufacture;

    public Device(String name, String image, String os, String manufacture) {
        this.name = name;
        this.image = image;
        this.os = os;
        this.manufacture = manufacture;
    }

    public String getName() {
        return  name;
    }

    public void setName(String name) { this.name=name; }
    public void setImage(String image) { this.image = image; }
    public void setOS(String os) { this.os=os; }
    public void setManufacture(String manufacture) { this.manufacture=manufacture; }

    public String getImage() {
        return image;
    }

    public String getOs() {
        return os;
    }

    public String getManufacture() {
        return manufacture;
    }
}

