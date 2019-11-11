package com.example.digitalevidence.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;

abstract public class Model {

    abstract public String getName();
    abstract public void setName(final String _name);
    abstract public String getLink();
    abstract public void setLink(final String _link);

}
