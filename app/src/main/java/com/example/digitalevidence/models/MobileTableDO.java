package com.example.digitalevidence.models;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "digitalforensics-mobilehub-1666815180-MobileTable")

public class MobileTableDO extends Model {
    private String _name;
    private String _brand;
    private String _link;
    private String _releasedate;
    private String _dimensions;

    public final static String TABLE_NAME = "digitalforensics-mobilehub-1666815180-MobileTable";

    @DynamoDBHashKey(attributeName = "name")
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

    @DynamoDBRangeKey(attributeName = "brand")
    @DynamoDBAttribute(attributeName = "brand")
    public String getBrand() {
        return _brand;
    }

    public void setBrand(final String _brand) {
        this._brand = _brand;
    }

    @DynamoDBAttribute(attributeName = "link")
    public String getLink() {
        return _link;
    }

    public void setLink(final String _link) {
        this._link = _link;
    }

    @DynamoDBRangeKey(attributeName = "releasedate")
    @DynamoDBAttribute(attributeName = "releasedate")
    public String getReleaseDate() {
        return _releasedate;
    }

    public void setReleaseDate(final String _releasedate) {
        this._releasedate = _releasedate;
    }

    @DynamoDBRangeKey(attributeName = "dimensions")
    @DynamoDBAttribute(attributeName = "dimensions")
    public String getDimensions() {
        return _dimensions;
    }

    public void setDimensions(final String _dimensions) {
        this._dimensions = _dimensions;
    }
}