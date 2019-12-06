package com.example.digitalevidence.models;
import androidx.annotation.NonNull;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "digitalforensics-mobilehub-1666815180-Mobile")
public class MobileDO {
    private String _name;
    private String _link;
    private String _releasedate;
    private String _dimensions;
    public static final MODEL_TYPE TYPE = MODEL_TYPE.MOBILE;
    public final static String TABLE_NAME = "digitalforensics-mobilehub-1666815180-Mobile";

    public MobileDO(){
        // Required empty public constructor
    }

    public MobileDO(String _name, String _link, String _releasedate, String _dimensions){
        this._name =_name;
        this._link = _link;
        this._releasedate =_releasedate;
        this._dimensions =_dimensions;
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
        MobileDO clone = new MobileDO(this._name, this._link, this._releasedate, this._dimensions);
        return clone;
    }
}