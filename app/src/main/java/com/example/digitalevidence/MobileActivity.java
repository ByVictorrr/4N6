package com.example.digitalevidence;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.DynamoDBEntry;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MobileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this).execute();

        // Needs to be async other wise it crashes
        new Thread(new Runnable() {
             public void run() {

                 AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
                AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

                // Add code to instantiate a AmazonDynamoDBClient
                AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

                // Declare a DynamoDBMapper object
                DynamoDBMapper dynamoDBMapper = DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient).awsConfiguration(configuration).build();
                dynamoDBMapper = DynamoDBMapper.builder()
                        .dynamoDBClient(dynamoDBClient)
                        .awsConfiguration(configuration)
                        .build();

                Table mTable = Table.loadTable(dynamoDBClient, "digitalforensics-mobilehub-1232538713-Mobile");
                Document d = mTable.getItem(new Primitive("iphonex"));
                Primitive link = (Primitive) d.get("link");

                Log.d("hi", "debug");




                // Step 4 - final step is to set all image views
                 String url = link.asString();
                 ImageView imgV = (ImageView)findViewById(R.id.imageView);
                // Picasso.get().load(url).into(imgV); // error here //TODO: fix this


                 Log.d("hi", "debug");

            }}).start();


            /*
            // Step 0 - get the tables from dynamod1
            DynamoHelper db = new DynamoHelper(1,  this);
            Document iphonex = db.getById("iphonex", db.MOBILE_TABLE_INDEX);
            Map<String, AttributeValue> iphonex_s = iphonex.toAttributeMap();
            Log.d("hi", iphonex_s.toString());


             */


       // db.setTables(db.getTables(this));

        // Step 1 - get all items in the db of table_index
       // List<Document> allItems = db.getAll(db.MOBILE_TABLE_INDEX, this);



        // Step 1 - define list of img views
        List<ImageView> imgV = Arrays.asList((ImageView)findViewById(R.id.imageView));
        List<String> str_urls  = Arrays.asList("https://yt3.ggpht.com/a/AGF-l7-BBIcC888A2qYc3rB44rST01IEYDG3uzbU_A=s900-c-k-c0xffffffff-no-rj-mo");
        // Step 2 - set image
       Picasso.get().load(str_urls.get(0)).into(imgV.get(0));



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
