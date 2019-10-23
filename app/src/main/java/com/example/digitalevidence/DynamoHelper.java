package com.example.digitalevidence;

import android.app.Activity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.UpdateItemOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.util.Tables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamoHelper {
    // for each type of activity i.e computer, mobile, ect..
    private List<Table> tables;
    private List<String> tableNames = Arrays.asList("mobile"); //, "computer", "storage", "misc");
    private static final String COGNITO_POOL_ID = "us-east-2:4a316b0c-77e0-4f36-8d6a-1a457af555f5";
    private static final Regions COGNITO_REGION= Regions.US_EAST_2;
    public  static final int MOBILE_TABLE_INDEX = 0;


    public void setTables(List<Table> t){
        this.tables = t;
    }
    public List<Table> getTables(){
        return this.tables;
    }


    private static CognitoCachingCredentialsProvider getCredProvider(Activity activity){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(activity.getApplicationContext(), COGNITO_POOL_ID, COGNITO_REGION);
        return credentialsProvider;
    }
    // Description: gets connection to the dynamoDB
    private static AmazonDynamoDBClient getClientConnection(Activity activity){
        CognitoCachingCredentialsProvider credentialsProvider = getCredProvider(activity);
        AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(credentialsProvider);
        return dbClient;
    }


    // Description: gets all the tables in dynamoDB
    public List<Table> getTables(Activity activity){
        AmazonDynamoDBClient dbClient = getClientConnection(activity);
        List<Table> tables = new ArrayList<>();
        for (int i = 0; i < tableNames.size(); i++) {
            tables.set(i, Table.loadTable(dbClient, tableNames.get(i)));
        }
        return tables;
    }

    // Description: get only one element in the tableIndex
    public Document getById(String id, int tableIndex, Activity activity){
        return tables.get(tableIndex).getItem(new Primitive(getCredProvider(activity).getCachedIdentityId()), new Primitive(id));
    }

    public List<Document> getAll(int tableIndex, Activity activity){
        return tables.get(tableIndex).query(new Primitive(getCredProvider(activity).getCachedIdentityId())).getAllResults();
    }

}

