package com.example.digitalevidence.models;
import java.util.Set;

public class Manufacturer {
    private String name;
    private String link;
    private Set<Device> devices;

    public String getName() {
        return name;
    }
    public String getLink() {
        return link;
    }
    public Set<Device> getDevices() {
        return devices;
    }

    public void setName(final String name) {
        this.name = name;
    }
    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
    public void setLink(String link){
        this.link = link;
    }
}