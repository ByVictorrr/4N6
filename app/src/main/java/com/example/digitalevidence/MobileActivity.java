package com.example.digitalevidence;

import android.annotation.TargetApi;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;



public class MobileActivity extends AppCompatActivity {
    public static List<String> urlNames; // used for getting the urls
    public static List<ImageView> imageViews; // used for setting


    public static boolean check = true;
    @Override
    @TargetApi(24) // to use java 8
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Step 0 - declare instance of dynamohelper
        DynamoHelper dynamoHelper = new DynamoHelper(this);
        // Step 1 - init image views
        imageViews = getImageViews();

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

                urlNames = mobiles.stream().map(objToLink()).collect(Collectors.toList());

                // Step 4 - set all urls to corresponding imageviews
                setImageViews(urlNames, imageViews);
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

    // Description: using lambda function to convert from mobile model to string (link)
    private Function<MobileDO, String> objToLink(){
        return (obj)->obj.getLink();
    }

    // Description: returns a list of imagesViews
    private List getImageViews(){
        List<ImageView> imgV = new ArrayList<>();
        imgV.add((ImageView)findViewById(R.id.imageView0));
        imgV.add((ImageView)findViewById(R.id.imageView1));
        imgV.add((ImageView)findViewById(R.id.imageView2));
        imgV.add((ImageView)findViewById(R.id.imageView3));
        imgV.add((ImageView)findViewById(R.id.imageView4));
        return imgV;
    }


}
