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
import com.example.digitalevidence.Views.DeviceView;

import java.text.AttributedCharacterIterator;
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
    // Maybe inroduce a new variable in dynamodb called id and rep the number
    public Thread getNItems(int NItems){

        return new Thread(new Runnable() {
            @Override
            public void run() {

                PaginatedList<MobileDO> list;
                Map<String, String > attributeNames = new HashMap<>();
                attributeNames.put("#name", "name");

                String prevText;
                if (previousDeviceView != null) {
                    prevText = (String) previousDeviceView.getTextView().getText();
                }else{
                    prevText = "amazon_phone";
                }



                Map<String, AttributeValue> attributeValues = new HashMap<>();
                attributeValues.put(":nameValue", new AttributeValue().withS(prevText));

                ScanRequest scanRequest = new ScanRequest()
                        .withTableName(MobileDO.TABLE_NAME)
                        .withExpressionAttributeNames(attributeNames)
                        .withFilterExpression("#name > :nameValue")
                        .withExpressionAttributeValues(attributeValues);
                List<Map<String, AttributeValue>> items;
                try {
                    ScanResult querynResult = dynamoDBClient.scan(scanRequest);
                    items = querynResult.getItems();
                }catch (Exception e){
                    Log.d("help", e.getMessage());
                }

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
