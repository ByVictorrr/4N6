package com.example.digitalevidence.Helpers;

import android.content.Context;
import android.util.Log;
import android.view.Display;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.digitalevidence.MobileActivity;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.Views.DeviceView;

import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.digitalevidence.MobileActivity.previousDeviceView;

public class DynamoHelper {
    private List<?> models; // list of data models (like moblileD0, ect..)
    private static CredentialHelper credentialHelper; //helper for all the functions
    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonDynamoDBClient dynamoDBClient;

    public DynamoHelper(Context context) {
        // step 1 - set credential Helper for specific activity
        credentialHelper = new CredentialHelper(context);
        // Step 2 - set inThread to False

        // Step 3 - configure all connections to Aws
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
        this.dynamoDBClient = new AmazonDynamoDBClient(credentialHelper.getCredentialsProvider());
        this.dynamoDBMapper= DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();

    }


    //Description: returns a list of models given an activity (context) and a type (model, ex: would be mobileD0)
    public Thread getAll() {
        // Step 1 - notify outside world that were in a thread
        return new Thread(new Runnable() {
            public void run() {
                // Step 2 - retrieve all items
                PaginatedList<MobileDO> list = dynamoDBMapper.scan(MobileDO.class, new DynamoDBScanExpression());
                setModels(list);
                // step 3 - notify that were are going out
            }
        });
    }


    // TODO: find a way to queue the data so we can load N items and it will hold last place in dynamo
    public Thread getNItems(final int NItems){

        return new Thread(new Runnable() {
            @Override
            public void run() {

                final int NAME = 0; // used for scan result indicators
                final int LINK = 1; // used for scan result indicators
                String prevText; // to compare lex graphically order
                Map<String, String > attributeNames = new HashMap<>();// used for scan request builder
                Map<String, AttributeValue> attributeValues = new HashMap<>(); // used for scan request builder
                List<Map<String, AttributeValue>> items; // used for scan Request ret items
                List<Model> models = new ArrayList<>(); // used to set the list

                // Step 1 - check to see if the first is loaded
                if (previousDeviceView != null) {
                    prevText = (String) previousDeviceView.getTextView().getText();
                }else{
                    prevText = "a";
                }

                // Step 2 - add to the attribute names/values
                attributeNames.put("#name", "name");
                attributeValues.put(":nameValue", new AttributeValue().withS(prevText));

                // Step 3 - build up the scanner for the query
                ScanRequest scanRequest = new ScanRequest()
                        .withTableName(MobileDO.TABLE_NAME)
                        .withExpressionAttributeNames(attributeNames)
                        .withFilterExpression("#name > :nameValue")
                        .withExpressionAttributeValues(attributeValues);

                // Step 4 - set the limit of how many items ret
                scanRequest.setLimit(NItems);
                ScanResult querynResult = dynamoDBClient.scan(scanRequest);
                // Step  5 - get all the items in a list of maps
                items = querynResult.getItems();
                // Step 6 - iterate throw the items
                for (Map<String, AttributeValue> item: items) {

                    // Step  7 - get items (i.e link and name)
                    //AttributeValue [] attributeValuess = (AttributeValue[]) (item.values().toArray());

                    // Step 8 - allocate space for a mobileDO
                    MobileDO mobileDO = new MobileDO();
                    for(int i = 0; i < item.values().toArray().length; i++) {

                        AttributeValue attributeValue = (AttributeValue) ((item.values().toArray())[i]);
                        String val = attributeValue.getS();
                        if (i == NAME) {
                            mobileDO.setName(val);
                        } else {
                            mobileDO.setLink(val);
                        }
                    }
                    //  step 9 - add that model to the models
                    models.add(mobileDO);
                }
                // Step 10 - add all the new items in models
                setModels(models);
            }
        });
    }

//=================================Setters/Getters==================================================\\


    public static CredentialHelper getCredentialHelper() {
        return credentialHelper;
    }


    public static DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    public void setModels(List<?> models) {
        this.models = models;
    }

    public List<?> getModels() {
        return models;
    }



//==================================================================================================\\

}
