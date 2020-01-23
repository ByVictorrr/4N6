package com.example.digitalevidence.helpers;
import android.annotation.TargetApi;
import android.content.Context;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.example.digitalevidence.models.Brand;
import com.example.digitalevidence.models.devices.Device;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamoHelper {

    private static DynamoHelper instance;
    private static ScanRequest scanRequest;
    private static AmazonDynamoDBClient dynamoDBClient;

    private Queue<Brand> brandsPending;

    public static DynamoHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DynamoHelper(context);
        }
        return instance;
    }

    @TargetApi(24)
    private DynamoHelper(Context context) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-west-2:66e0d23a-4c2c-46eb-ad73-b1174813e1b5", // Identity pool ID
                Regions.US_WEST_2 // Region
        );

        scanRequest=new ScanRequest().withConsistentRead(true);
        this.dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        this.dynamoDBClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        this.brandsPending = new PriorityQueue(Comparator.comparing(Brand::getName));


    }


    public Thread fetchBrands(final String TABLE_NAME, final Integer loadNum){
        return new Thread(new Runnable(){
            @Override
            public void run() {
                List<Map<String, AttributeValue>> items;

                scanRequest = scanRequest
                        .withTableName(TABLE_NAME)
                        .withLimit(loadNum)
                        .withConsistentRead(true);

                items = dynamoDBClient
                        .scan(scanRequest)
                        .getItems();

                // Go Through and Allocate Each Items
                for (Map<String, AttributeValue> item : items) {
                    Brand brand = new Brand();
                    parseBrand(item, brand);
                    brandsPending.add(brand);
                }

            }
        });
    }


    @TargetApi(24)
    void parseBrand(Map<String, AttributeValue> src, Brand des){
        final Integer NAME=0, LINK=1, DIMENSIONS=2, DATE_RELEASED=3;

        Object[] objects = src.values().toArray();
        List<Device> deviceList = new ArrayList<>();

        String brandName = ((AttributeValue)(objects[0])).getS();
        String link;
        AttributeValue attributeValue = (AttributeValue) (objects[1]);

        List<AttributeValue> devices = attributeValue.getL();
        link = devices.get(0).getS();

        for(AttributeValue mess_device: devices.get(1).getL()){
            List<AttributeValue>  attributes = mess_device.getL();
            List<String> s_attributes = new ArrayList<>();
            for(AttributeValue attribute: attributes){
                s_attributes.add(attribute.getS());
            }
            Device device = new Device(s_attributes.get(NAME),
                    s_attributes.get(LINK),
                    s_attributes.get(DATE_RELEASED),
                    s_attributes.get(DIMENSIONS)
            );
            deviceList.add(device);
        }

        des.setDevices(deviceList.stream().collect(Collectors.toSet()));
        des.setName(brandName);
        des.setLink(link);
    }
    public Queue<Brand> getBrandsPending(){return this.brandsPending;}

}