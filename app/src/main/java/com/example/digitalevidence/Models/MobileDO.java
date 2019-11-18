package com.example.digitalevidence.Models;

import androidx.annotation.NonNull;
import androidx.transition.Visibility;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "digitalforensics-mobilehub-1666815180-Mobile")

public class MobileDO extends Model implements Cloneable{
    private String _name;
    private String _link;
    public static final MODEL_TYPE TYPE = MODEL_TYPE.MOBILE;
    public final static String TABLE_NAME = "digitalforensics-mobilehub-1666815180-Mobile";

    public MobileDO(){}
    public MobileDO(String _name, String _link){
        this._name =_name;
        this._link = _link;
    }
    @DynamoDBHashKey(attributeName = "name")
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }
    public void setName(final String _name) {
        this._name = _name;
    }

    @DynamoDBAttribute(attributeName = "link")
    public String getLink() {
        return _link;
    }

    public void setLink(final String _link) {
        this._link = _link;
    }

    @NonNull
    @Override
    public Object clone() {
        MobileDO clone = new MobileDO(this._name, this._link);
        return clone;
    }
}




