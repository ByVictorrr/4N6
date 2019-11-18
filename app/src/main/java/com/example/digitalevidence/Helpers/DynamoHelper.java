package com.example.digitalevidence.Helpers;

import android.annotation.TargetApi;
import android.content.Context;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.digitalevidence.Models.MODEL_TYPE;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Queue;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


// Description: One DynamoHelper per activity
public class DynamoHelper {
    private Queue<Model> modelsPending; // list of data modelsPending (like moblileD0, ect..)
    private MODEL_TYPE type;
    private String tableName;
    private ScanResult scanResult; // used to get prev
    private ScanRequest scanRequest = new ScanRequest().withConsistentRead(true);
    private Map<String, AttributeValue> lastKeyEvaluated = null;


    private static DynamoDBMapper dynamoDBMapper;
    private static CredentialHelper credentialHelper; //helper for all the functions
    private static AmazonDynamoDBClient dynamoDBClient;

    // Description: Used to determine to what data object to reference in getNitems
    public Model mapClassToModel(MODEL_TYPE type){
        Model model = null;
        switch (type){
            case MOBILE:
                model = new MobileDO();
                break;
            case COMPUTER:
                model = new MobileDO();
                //model = new ComputerDO();
                break;
            case MISC:
                model = new MobileDO();
                //model = new MiscDO();
                break;
            case STORAGE:
                model = new MobileDO();
                //model = new StorageDO();
                break;

        }

        return model;
    }

    @TargetApi(24)
    public DynamoHelper(Context context, MODEL_TYPE type, String tableName) {
        // step 1 - set credential Helper for specific activity
        credentialHelper = new CredentialHelper(context);
        // Step 2 - see what activity were in
        this.type = type;
        this.tableName = tableName;

        // Step 3 - configure all connections to Aws
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
        this.dynamoDBClient = new AmazonDynamoDBClient(credentialHelper.getCredentialsProvider());
        this.dynamoDBMapper= DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();
        this.modelsPending = new PriorityQueue<>(Comparator.comparing(Model::getName));
    }

    private int count = 0;

    // TODO: find a way to queue the data so we can load N items and it will hold last place in dynamo
    public Thread getNItems(final int NItems){

        return new Thread(new Runnable() {
            @Override
            public void run() {

                final int NAME = 0; // used for scan result indicators
                final int LINK = 1; // used for scan result indicators
                List<Map<String, AttributeValue>> items; // used for scan Request ret items

                // Step 4 - set the limit of how many items ret
                    scanRequest = scanRequest
                            .withTableName(tableName)
                            .withLimit(NItems)
                            .withExclusiveStartKey(lastKeyEvaluated)
                            .withConsistentRead(true);

                    scanResult = dynamoDBClient.scan(scanRequest);
                    lastKeyEvaluated = scanResult.getLastEvaluatedKey();

                // Step  5 - get all the items in a list of maps
                items = scanResult.getItems();
                // Step 6 - iterate throw the items
                for (Map<String, AttributeValue> item: items) {

                    // Step 8 - allocate space for a model
                    Model modelDO = mapClassToModel(type);

                    for(int i = 0; i < item.values().toArray().length; i++) {

                        AttributeValue attributeValue = (AttributeValue) ((item.values().toArray())[i]);
                        String val = attributeValue.getS();
                        if (i == NAME) {
                            modelDO.setName(val);
                        } else {
                            modelDO.setLink(val);
                        }
                    }
                    //  step 9 - add that model to the modelsPending
                    modelsPending.add(modelDO);
                }
            }
        });
    }

    public Queue<Model> getModelsPending() {
        return modelsPending;
    }

}
