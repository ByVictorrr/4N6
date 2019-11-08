package com.example.digitalevidence;

import android.annotation.TargetApi;
import android.media.Image;
import android.os.Bundle;

import com.example.digitalevidence.Helpers.DynamoHelper;
import com.example.digitalevidence.Models.MobileDO;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;



public class MobileActivity extends AppCompatActivity {
    // TODO : make urlNames and names a map
    private static List<String> urlNames; // used for getting the urls
    private static List<String> names; //phone names

    private static List<ImageView> imageViews; // used for setting
    private static List<TextView> textViews;


    @Override
    @TargetApi(24) // to use java 8
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Step 0 - declare instance of dynamohelper
        DynamoHelper dynamoHelper = new DynamoHelper(this);
        // Step 1 - init image views and text views
        imageViews = getImageViews();
        textViews = getTextViews();

        // Step 2 - get all urls from dynamo helper (for mobile objects)
        Thread getAll = dynamoHelper.getAll(MobileDO.class);

        // Step 3 - Wait while threads are finishing and set urls images to background
        Thread doAll = doAll(dynamoHelper);


        Log.d("1", "try");
        try {
            Log.d("2", "entering thread");
            getAll.start();

            Log.d("3", "entering join");
            getAll.join();

            Log.d("4", "entering do all start");
            doAll.start();

            Log.d("5", "after do all start");

            Log.d("6", "entering join");
            doAll.join();


        }catch (Exception e){
            e.getLocalizedMessage();
        }


        Log.d("6", "end of main");

    }


    @TargetApi(24) // to use java 8
    private Thread doAll (DynamoHelper dynamoHelper){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                List <MobileDO> mobiles = (List<MobileDO>)dynamoHelper.getModels();

                names = mobiles.stream().map(MobileDO::getName).collect(Collectors.toList());
                urlNames = mobiles.stream().map(MobileDO::getLink).collect(Collectors.toList());

                // Step 4 - set all urls to corresponding imageviews
                setImageViews(urlNames, imageViews);
                setTextViews(names, textViews);
            }
        });
    }


    private void setImageViews(List<String> urlNames, List<ImageView> imageViews){

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            ImageView iv;
            @Override
            public void run() {
               for(int i = 0; i < urlNames.size(); i++) {
                    iv = imageViews.get(i);
                    Picasso.get().load(urlNames.get(i)).into(iv);
            }
            }});
        }

    private void setTextViews(List<String> names, List<TextView> textViews){
        for (int i = 0; i< textViews.size(); i++)
            textViews.get(i).setText(names.get(i));
    }


    // Description: returns a list of imagesViews
    private List<ImageView> getImageViews(){
        List<ImageView> imgV = new ArrayList<>();
        imgV.add((ImageView)findViewById(R.id.imageView0));
        imgV.add((ImageView)findViewById(R.id.imageView1));
        imgV.add((ImageView)findViewById(R.id.imageView2));
        imgV.add((ImageView)findViewById(R.id.imageView3));
        imgV.add((ImageView)findViewById(R.id.imageView4));
        imgV.add((ImageView)findViewById(R.id.imageView5));
        imgV.add((ImageView)findViewById(R.id.imageView6));
        return imgV;
    }
    private List<TextView> getTextViews() {

        List<TextView> txtV = new ArrayList<>();
        txtV.add((TextView)findViewById(R.id.textView0));
        txtV.add((TextView)findViewById(R.id.textView1));
        txtV.add((TextView)findViewById(R.id.textView2));
        txtV.add((TextView)findViewById(R.id.textView3));
        txtV.add((TextView)findViewById(R.id.textView4));
        txtV.add((TextView)findViewById(R.id.textView5));
        txtV.add((TextView)findViewById(R.id.textView6));
        return txtV;
    }


}
