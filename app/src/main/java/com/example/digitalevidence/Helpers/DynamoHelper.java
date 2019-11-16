package com.example.digitalevidence.Helpers;

import android.annotation.TargetApi;
import android.content.Context;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


// Description: One DynamoHelper per activity
public class DynamoHelper {
    private Deque<Model> modelsPending; // list of data modelsPending (like moblileD0, ect..)
    private Class modelClass; // to indicate what data object were working with
    private String tableName;
    public  Model previousModel;
    private ScanResult queryResult; // used to get prev


    private static DynamoDBMapper dynamoDBMapper;
    private static CredentialHelper credentialHelper; //helper for all the functions
    private static AmazonDynamoDBClient dynamoDBClient;


    // Description: Used to determine to what data object to reference in getNitems
    private static Dictionary<Class, Model> mapClassToModel;
    static {
        mapClassToModel = new Hashtable<>();
        //mapClassToModel.put(ComputerDO.class, new ComputerDO());
        //mapClassToModel.put(MiscDO.class, new MiscDO());
        //mapClassToModel.put(StorageDO.class, new StorageDO());
        mapClassToModel.put(MobileDO.class, new MobileDO());

    }

    //
    @TargetApi(24)
    public DynamoHelper(Context context, Class classType, String tableName) {
        // step 1 - set credential Helper for specific activity
        credentialHelper = new CredentialHelper(context);
        // Step 2 - see what activity were in
        this.modelClass = classType;
        this.tableName = tableName;

        // Step 3 - configure all connections to Aws
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
        this.dynamoDBClient = new AmazonDynamoDBClient(credentialHelper.getCredentialsProvider());
        this.dynamoDBMapper= DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();
        this.modelsPending = new ArrayDeque<>();
    }

    private int count = 0;

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



                // Step 1 - check to see if the first is loaded
                if (queryResult != null) {
                    prevText =(String)((AttributeValue)(queryResult.getLastEvaluatedKey().values().toArray()[NAME])).getS();
                }else{
                    prevText = "z*";
                }

                count++;
                // Step 2 - add to the attribute names/values
                attributeNames.put("#name", "name");
                attributeValues.put(":nameValue", new AttributeValue().withS(prevText));

                // Step 3 - build up the scanner for the query
                ScanRequest scanRequest = new ScanRequest()
                        .withTableName(tableName)
                        .withExpressionAttributeNames(attributeNames)
                        .withFilterExpression("#name <> :nameValue")
                        .withExpressionAttributeValues(attributeValues);

                // Step 4 - set the limit of how many items ret
                scanRequest.setLimit(NItems);
                queryResult = dynamoDBClient.scan(scanRequest);
                // Step  5 - get all the items in a list of maps
                items = queryResult.getItems();
                // Step 6 - iterate throw the items
                for (Map<String, AttributeValue> item: items) {

                    // Step 8 - allocate space for a model
                    MobileDO modelDO = new MobileDO();
                    //Model modelDO = (Model)(mapClassToModel.get(modelClass);

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
                // Step 10 - set last to be previous model
                //previousModel =  modelsPending.peekLast();
            }
        });
    }




    public Deque<Model> getModelsPending() {
        return modelsPending;
    }

}
