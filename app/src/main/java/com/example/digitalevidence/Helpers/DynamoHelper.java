package com.example.digitalevidence.Helpers;

import android.content.Context;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.digitalevidence.Models.MobileDO;


import java.lang.reflect.Type;
import java.util.List;

public class DynamoHelper {
    private List<?> models; // list of data models (like moblileD0, ect..)
    private static Boolean inThread; // used to check to see if your in a thread
    private static CredentialHelper credentialHelper; //helper for all the functions
    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonDynamoDBClient dynamoDBClient;
    private static Document item; // if we call read item with the id it will be stored here

    public DynamoHelper(Context context) {
        // step 1 - set credential Helper for specific activity
        credentialHelper = new CredentialHelper(context);
        // Step 2 - set inThread to False
        inThread = false;

        // Step 3 - configure all connections to Aws
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
        this.dynamoDBClient = new AmazonDynamoDBClient(credentialHelper.getCredentialsProvider());
        this.dynamoDBMapper= DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();

    }



/*
    // Description: gets a Single value based off its id // TODO : use Dynamomapper to get the one item
    void getItem(final String id, final Model model){ //TODO: use model to get table name
        // how can we use parameters to get fed to run()  - use final variables
        new Thread(new Runnable() {
            @Override
            public void run() {
                Table table = Table.loadTable(dynamoDBClient, model.getTableName());
                Document d = table.getItem(new Primitive(id));
                setItem(d);
            }
        }).start();

    }

 */

    //Description: returns a list of models given an activity (context) and a type (model, ex: would be mobileD0)
    public Thread getAll(final Type type) {
        // Step 1 - notify outside world that were in a thread
        setInThread(true);
        return new Thread(new Runnable() {
            public void run() {
                // Step 2 - retrieve all items
                PaginatedList<MobileDO> list = dynamoDBMapper.scan(MobileDO.class, new DynamoDBScanExpression());
                setModels(list);
                // step 3 - notify that were are going out
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

    public static void setItem(Document item) {
        DynamoHelper.item = item;
    }

    // Description: in activities we might need to wait for threads to finish (this indicates)
    public Boolean getInThread() {
        return inThread;
    }

    // Description: helper function to set if inside a thread or not
    private void setInThread(Boolean inThread) {
        this.inThread = inThread;
    }


//==================================================================================================\\

}
