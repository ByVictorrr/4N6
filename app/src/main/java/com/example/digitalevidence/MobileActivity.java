package com.example.digitalevidence;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.util.Log;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


/*
        // Step 0 - get the tables from dynamodb
        DynamoHelper db = new DynamoHelper();
        db.setTables(db.getTables(this));

        // Step 1 - get all items in the db of table_index
        List<Document> allItems = db.getAll(db.MOBILE_TABLE_INDEX, this);


        // Step 2 - translate documents into urls

*/

        /*// Step 1 - define list of img views
        List<ImageView> imgV = Arrays.asList((ImageView)findViewById(R.id.imageView));
        List<String> urls  = Arrays.asList("https://cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg");
        // Step 2 - set the list of img views and url
        setImageView(imgV);
        // step 3 -  change pictures for each img view
        changePictureImageView(urls, imgV);*/

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
