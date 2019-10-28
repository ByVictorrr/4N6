package com.example.digitalevidence;

import android.app.Activity;
import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.ScanOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Search;
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
    private List<String> tableNames = Arrays.asList("Mobile"); //, "computer", "storage", "misc");
    private static final String COGNITO_POOL_ID = "us-east-2:ae114fca-5629-47cd-aeb6-82090028f6be";
    private static final Regions COGNITO_REGION= Regions.US_EAST_2;
    public  static final int MOBILE_TABLE_INDEX = 0;


    public DynamoHelper(int table_size, Activity activity){
        //CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(activity, COGNITO_POOL_ID, COGNITO_REGION);
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                activity,
                "us-east-2:4a316b0c-77e0-4f36-8d6a-1a457af555f5", // Identity pool ID
                Regions.US_EAST_2 // Region
        );
        AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(credentialsProvider);
        this.tables = new ArrayList<>(table_size);
        for (int i = 0; i < tableNames.size(); i++) {
            tables.set(i, Table.loadTable(dbClient, tableNames.get(i)));
        }
    }

    public List<Table> getTables(){ return this.tables; }


    // Description: get only one element in the tableIndex
    public Document getById(String id, int tableIndex){
        return tables.get(tableIndex).getItem(new Primitive(id));
    }
/*
    public List<Document> getAll(int tableIndex, Activity activity){
        ScanOperationConfig scanConfig = new ScanOperationConfig();
        List<String> attributeList = new ArrayList<>();
        attributeList.add("name");
        scanConfig.withAttributesToGet(attributeList);
        Search searchResult = dbTable.scan(scanConfig);
        return searchResult.getAllResults();
    }
*/


}

