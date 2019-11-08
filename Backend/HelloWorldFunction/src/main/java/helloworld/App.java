package helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Models.MobileDO;
import Utility.Pair;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.*;

import static com.amazonaws.auth.profile.internal.ProfileKeyConstants.REGION;
import static software.amazon.ion.impl.PrivateIonConstants.True;


/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<S3EventNotification, Object> {

    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "digitalforensics-mobilehub-1666815180-Mobile";
    private Regions REGION = Regions.US_EAST_2;

    private String urlBuilder(String key, String bucketName) {
        //return "https://" + bucketName + ".s3.amazonaws.com/" + key;
        return "https://digitalforensics-userfiles-mobilehub-1666815180.s3.us-east-2.amazonaws.com/" + key;
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

    public Object handleRequest(final S3EventNotification input, final Context context) {
        System.out.print("my input " + input);
        // Step 1 - get newly inserted objects urls
        List<Pair<String, String>> namesAndS3References = namesAndS3References(input);
        // Step 2 - upload the name or key
        this.initDynamoDbClient();
        // Step 3 - insert each one to dynamo
        try {
            for (Pair<String, String> nameAndURL : namesAndS3References) {
                persistData(new MobileDO(nameAndURL.getKey(), nameAndURL.getValue()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return True;
    }


    //================================Dynamodb init=======================\\

    private PutItemOutcome persistData(MobileDO mobileRequest)
            throws ConditionalCheckFailedException {
            return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withString("name", mobileRequest.getName())
                                .withString("link", mobileRequest.getLink())));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }
}
