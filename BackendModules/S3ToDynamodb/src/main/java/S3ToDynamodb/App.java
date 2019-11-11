package helloworld;

import Models.MobileDO;
import Utility.Pair;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<S3Event, Object> {

    private Regions REGION = Regions.US_EAST_2;
    private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
    private DynamoDB dynamoDb = new DynamoDB(client);
    private String DYNAMODB_TABLE_NAME = "digitalforensics-mobilehub-1666815180-Mobile";

    /*
    public Object handleRequest(final S3Event input, final Context context) {
        System.out.print("my input victor " + input.toString());
        // Step 1 - get newly inserted objects urls

        Pair<String, String> namesAndS3References = namesAndS3References(input).get(0);

        System.out.println("names and ref fine");
        // Step 3 - insert each one to dynamo
        try {
            putItem(new MobileDO(namesAndS3References.getKey(), namesAndS3References.getValue()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return True;
    }
    */


    public Object handleRequest(final S3Event input, final Context context) {
        System.out.print("my input victor " + input.toString());
        // Step 1 - get newly inserted objects urls

        List<Pair<String, String>> namesAndS3References = namesAndS3References(input);

        System.out.println("names and ref fine");
        // Step 3 - insert each one to dynamo
        try {
            for (Pair<String, String> nameAndURL : namesAndS3References) {
                putItem(new MobileDO(nameAndURL.getKey(), nameAndURL.getValue()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }



    // Description: returns a list picture names and urls of the newly inserted s3 objects.
    private List<Pair<String, String>> namesAndS3References(final S3EventNotification input) {
        List<Pair<String, String>> namesAndRef;
        try {
            List<S3EventNotificationRecord> records = input.getRecords();
            namesAndRef = new ArrayList<>(records.size());

            for (S3EventNotificationRecord record : records) {
                String key = record.getS3().getObject().getKey();
                String bucketName = record.getS3().getBucket().getName(); // TODO : for only one table/bucket right now make for List<Pairs> per bucket that is make a Map<String bucketName, List<Pair>>
                String name = Paths.get(key.substring(0, key.lastIndexOf('.'))).getFileName().toString(); // removes picture extension getting the name // TODO needs to take off sub directories

                namesAndRef.add(new Pair(name, urlBuilder(key, bucketName))); // pair(name of device, url of device)
            }
            return namesAndRef;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String urlBuilder(String key, String bucketName) {
        //return "https://" + bucketName + ".s3.amazonaws.com/" + key;
        return "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/" + key;
    }


    //================================Dynamodb init=======================\\


    private void putItem(MobileDO mobileDO){
        Table table = dynamoDb.getTable(DYNAMODB_TABLE_NAME);
        System.out.println(dynamoDb);
        System.out.println(client);
        System.out.print(table);
        try{
           Item item = new Item().withPrimaryKey("name", mobileDO.getName()).withString("link", mobileDO.getLink());
           table.putItem(item);
            System.out.println("Created item");
        }catch (Exception e){
            System.out.println("Created item failed");
            System.out.println(e.getMessage());
        }
    }

}
