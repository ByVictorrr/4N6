package com.example.digitalevidence.helpers;
import android.annotation.TargetApi;
import android.content.Context;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.digitalevidence.models.Manufacturer;
import com.example.digitalevidence.models.Device;
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
    private Queue<Manufacturer> brandsPending;
    private Queue<Device> devicesPending;
    private String TableName;
    private int loadNum;

    public static DynamoHelper getInstance(Context context, String Table, int loadNum) {
        if (instance == null) {
            instance = new DynamoHelper(context, Table, loadNum);
        }
        return instance;
    }

    @TargetApi(24)
    private DynamoHelper(Context context, String Table, int loadNum) {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-west-2:66e0d23a-4c2c-46eb-ad73-b1174813e1b5", // Identity pool ID
                Regions.US_WEST_2
        );

        scanRequest=new ScanRequest()
                .withTableName(Table)
                .withLimit(loadNum)
                .withConsistentRead(true);

        this.dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        this.dynamoDBClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        this.brandsPending = new PriorityQueue(Comparator.comparing(Manufacturer::getName));
    }

    public Thread fetchBrands(){
        return new Thread(new Runnable(){
            @Override
            public void run() {
                List<Map<String, AttributeValue>> items;
                ScanResult result;

                result = dynamoDBClient.scan(scanRequest);
                items = result.getItems();
                Map<String, AttributeValue> lastEvaluatedKey = result.getLastEvaluatedKey();

                scanRequest.setExclusiveStartKey(lastEvaluatedKey);

                // Go Through and Allocate Each Items
                for (Map<String, AttributeValue> item : items) {
                    Manufacturer manufacturer = new Manufacturer();
                    parseBrand(item, manufacturer);
                    brandsPending.add(manufacturer);
                }
            }
        });
    }

    /*public Thread fetchDevices(){
        return new Thread(new Runnable(){
            @Override
            public void run() {
                List<Map<String, AttributeValue>> items;
                ScanResult result;

                result = dynamoDBClient.scan(scanRequest);
                items = result.getItems();
                Map<String, AttributeValue> lastEvaluatedKey = result.getLastEvaluatedKey();

                scanRequest.setExclusiveStartKey(lastEvaluatedKey);

                // Go Through and Allocate Each Items
                for (Map<String, AttributeValue> item : items) {
                    Device device = new Device();
                    parseDevice(item, device);
                    devicesPending.add(device);
                }
            }
        });
    }*/

    @TargetApi(24)
    void parseBrand(Map<String, AttributeValue> src, Manufacturer des){
        Object[] objects = src.values().toArray();
        List<Device> deviceList = new ArrayList<>();

        String brandName = ((AttributeValue)(objects[0])).getS();
        String link;
        AttributeValue attributeValue = (AttributeValue) (objects[1]);

        List<AttributeValue> devices = attributeValue.getL();
        link = devices.get(0).getS();

        for(AttributeValue mess_device: devices.get(1).getL()) {

            Map<String, AttributeValue> attributes = mess_device.getM();

            Device device = new Device(
                    attributes.get("name").getS(),
                    attributes.get("image").getS(),
                    attributes.get("os").getS(),
                    attributes.get("manufacturer").getS()
            );

            deviceList.add(device);
        }

        des.setDevices(deviceList.stream().collect(Collectors.toSet()));
        des.setName(brandName);
        des.setLink(link);
    }

    /*@TargetApi(24)
    void parseDevice(List<Device> src, Device des){
        Object[] objects = src.toArray();

        String deviceName = ((AttributeValue)(objects[0])).getS();
        String image = ((AttributeValue)(objects[1])).getS();
        String os = ((AttributeValue)(objects[2])).getS();

        des.setName(deviceName);
        des.setImage(image);
        des.setOS(os);
    }*/

    public Queue<Manufacturer> getBrandsPending(){return this.brandsPending;}

    public Queue<Device> getDevicesPending(){return this.devicesPending;}
}