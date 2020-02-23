package com.example.digitalevidence.models;

import java.util.Set;


public class Manufacturer {
    private String name;
    private String link;
    private Set<Device> devices;

    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
    public void setLink(String link){
        this.link=link;
    }

    public Set<Device> getDevices() {
        return devices;
    }
}
