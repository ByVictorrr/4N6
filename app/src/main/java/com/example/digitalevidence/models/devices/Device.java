package com.example.digitalevidence.models.devices;
import androidx.annotation.NonNull;

public class Device {
    protected String name, link, dimensions, releaseDate;

    public Device(String name, String link, String dimensions, String releaseDate) {
        this.name = name;
        this.link = link;
        this.dimensions = dimensions;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return  name;
    }

    public void setName(String _name) {
        this.name=_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String _link) {
        this.link=_link;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String _releasedate) {
        releaseDate=_releasedate;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String _dimensions) {
        dimensions=_dimensions;
    }
}

