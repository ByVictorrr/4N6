package helloworld.Models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "digitalforensics-mobilehub-1666815180-Mobile")

public class MobileDO{
    private String _name;
    private String _link;

    public MobileDO(String name, String link){
        _name = name;
        _link = link;
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

}




