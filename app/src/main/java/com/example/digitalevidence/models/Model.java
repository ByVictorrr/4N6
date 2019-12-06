package com.example.digitalevidence.models;
import androidx.annotation.NonNull;

abstract public class Model{
    abstract public String getName();
    abstract public void setName(final String _name);
    abstract public String getLink();
    abstract public void setLink(final String _link);
    abstract public String getBrand();
    abstract public void setBrand(final String _brand);
    abstract public String getReleaseDate();
    abstract public void setReleaseDate(final String _releasedate);
    abstract public String getDimensions();
    abstract public void setDimensions(final String _dimensions);


    @NonNull
    @Override
    protected Object clone(){
        try {
            return super.clone();
        }
        catch (Exception e){
            e.getLocalizedMessage();
        }
        return null;
    }
}

