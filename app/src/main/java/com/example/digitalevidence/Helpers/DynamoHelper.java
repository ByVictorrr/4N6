package com.example.digitalevidence.Helpers;

import android.content.Context;
import android.view.Display;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;

import java.util.List;

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
    public Thread getNItems(int scrollLoc){

        return new Thread(new Runnable() {
            @Override
            public void run() {

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
