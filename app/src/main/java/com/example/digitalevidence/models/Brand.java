package com.example.digitalevidence.models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.example.digitalevidence.models.devices.Device;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class Brand{
    private String _name;
    private String link;
    private Set<Device> devices;

    public String getName() {
        return _name;
    }
    public void setName(final String _name) {
        this._name = _name;
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
