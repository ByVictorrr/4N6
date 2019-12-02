package com.example.digitalevidence.helpers;
import android.annotation.TargetApi;
import android.content.Context;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.digitalevidence.models.MODEL_TYPE;
import com.example.digitalevidence.models.MobileDO;
import com.example.digitalevidence.models.MobileTableDO;
import com.example.digitalevidence.models.Model;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// Description: One DynamoHelper per activity
public class DynamoHelper {
    private Queue<Model> modelsPending; // list of data modelsPending (like moblileD0, ect..)
    private MODEL_TYPE type;
    private String tableName;
    private ScanResult scanResult; // used to get prev
    private ScanRequest scanRequest = new ScanRequest().withConsistentRead(true);
    private Map<String, AttributeValue> lastKeyEvaluated = null;


    //======================================
    private QueryRequest queryRequest = new QueryRequest().withConsistentRead(true);
    private QueryResult queryResult;


    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonDynamoDBClient dynamoDBClient;

    // Used to Determine what Data Object Referenced in getNitems()
    public Model mapClassToModel(MODEL_TYPE type){
        Model model = null;
        switch (type){
            case MOBILE:
                model = new MobileTableDO();
                break;
            case COMPUTER:
                model = new MobileTableDO();
                break;
            case MISC:
                model = new MobileTableDO();
                break;
            case STORAGE:
                model = new MobileTableDO();
                break;
        }
        return model;
    }

    @TargetApi(24)
    public DynamoHelper(Context context, MODEL_TYPE type, String tableName) {
        // Set Credential Helper for Activity
        CredentialHelper credentialHelper = new CredentialHelper(context);

        // Find Activity Located In
        this.type = type;
        this.tableName = tableName;

        // Configure All Connections to AWS
        if (dynamoDBClient == null || dynamoDBMapper == null) {
            AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
            this.dynamoDBClient = new AmazonDynamoDBClient(credentialHelper.getCredentialsProvider());
            this.dynamoDBMapper = DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();
        }
        this.modelsPending = new LinkedList<>();
    }

    private String prev;

    public Thread getNItems(final int NItems){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                final int NAME = 0;
                final int LINK = 1;

                List<Map<String, AttributeValue>> items;

                Condition hashkeycond = new Condition();

                // Set Limit of Items Returned
                    scanRequest = scanRequest
                            .withTableName(tableName)
                            .withLimit(NItems)
                            .withExclusiveStartKey(lastKeyEvaluated)
                            .withConsistentRead(true);


                    scanResult = dynamoDBClient.scan(scanRequest);
                    //queryResult = dynamoDBClient.query(queryRequest);

                    //lastKeyEvaluated = scanResult.getLastEvaluatedKey();
                    lastKeyEvaluated = queryRequest.getExclusiveStartKey();

                // Put Items in a List
                items = scanResult.getItems();

                // Go Through and Allocate Each Items
                for (Map<String, AttributeValue> item: items) {
                    Model modelDO = mapClassToModel(type);

                    for(int i = 0; i < item.values().toArray().length; i++) {
                        AttributeValue attributeValue = (AttributeValue) ((item.values().toArray())[i]);
                        String val = attributeValue.getS();
                        if (i == NAME) {
                            modelDO.setName(val);
                        } else if (i == LINK){
                            modelDO.setLink(val);
                        }else{
                            modelDO.setBrand(val);
                        }
                    }

                    modelsPending.add(modelDO);
                }
            }
        });
    }

    public Queue<Model> getModelsPending() {
        return modelsPending;
    }
}
