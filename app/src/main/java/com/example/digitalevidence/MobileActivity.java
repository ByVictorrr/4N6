package com.example.digitalevidence;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.document.ScanFilter;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Search;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.DynamoDBEntry;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.mobileconnectors.dynamodbv2.document.internal.KeyDescription;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.digitalevidence.Models.MobileDO;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.jar.Attributes;

public class MobileActivity extends AppCompatActivity {

    private static List<String> urlNames; // used for getting the urls
    private static List<ImageView> imageViews; // used for setting
    private static boolean inThread;
    private final String TABLE_NAME = "digitalforensics-mobilehub-1666815180-Mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Step 1 - init to null so we know when the thread is done
        urlNames = null;


        // Needs to be async other wise it crashes
        new Thread(new Runnable() {

             public void run() {

                 // Initialize the Amazon Cognito credentials provider
                 CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                         getApplicationContext(),
                         "us-east-2:9bf3662b-58e0-440e-aaec-a03b002c8c4a", // Identity pool ID
                         Regions.US_EAST_2 // Region
                 );

                 inThread = true;

                 urlNames = new ArrayList<>();
                 //AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
                 AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
                 // Add code to instantiate a AmazonDynamoDBClient
                 AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

                // Declare a DynamoDBMapper object
                DynamoDBMapper dynamoDBMapper = DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();

                Table mTable = Table.loadTable(dynamoDBClient, TABLE_NAME);

                List<AttributeDefinition> val = mTable.getAttributes();

                List<String> s = mTable.getRangeKeys();
                Map<String, KeyDescription> m = mTable.getKeys();


                ScanRequest request =  new ScanRequest(TABLE_NAME);
                Map<String, AttributeValue> requests = request.getExclusiveStartKey();


               // Search s1 = mTable.query(new Primitive("SELECT * FROM " + TABLE_NAME));

                Document d = mTable.getItem(new Primitive("iphonex"));
                Primitive link = (Primitive) d.get("link");

                urlNames.add(link.asString());
                Log.d("hi", "debug");
                ScanFilter f;

                Log.d("hi", "debug");


                // trying to get all items
                 PaginatedList<MobileDO> ss = dynamoDBMapper.scan(MobileDO.class, new DynamoDBScanExpression());
                 ss.get(0).getLink();


                inThread = false;


            }}).start();

        //List<ImageView> imgV = Arrays.asList((ImageView)findViewById(R.id.imageView));
        //List<String> str_urls  = Arrays.asList("https://yt3.ggpht.coIm/a/AGF-l7-BBIcC888A2qYc3rB44rST01IEYDG3uzbU_A=s900-c-k-c0xffffffff-no-rj-mo");
        // Step 2 - set image
        ImageView imgV = (ImageView)findViewById(R.id.imageView);

        while(urlNames != null && !inThread) {
            Picasso.get().load(urlNames.get(0)).into(imgV);
            break;
        }



    }

    //TODO : fix - maybe return a list instead
    void setImageView(List<ImageView> img){
        //img.set(0, (ImageView)findViewById(R.id.imageView));
        //img.set(1, (ImageView)findViewById(R.id.imageView));
        //img.set(2, (ImageView)findViewById(R.id.imageView));

    }

    void changePictureImageView(List<String> urls, List<ImageView> img) {
        URL newurl;
        for (int i = 0; i < urls.size(); i++) {
            try {
                // step 0 - get the url and then set the image view
                newurl = new URL(urls.get(i));
                // step 1 - put all images in the templates for each image view;
                img.get(i).setImageBitmap(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
            } catch(Exception e){
                Log.d("hi", e.getMessage());
            }
        }
    }

}
