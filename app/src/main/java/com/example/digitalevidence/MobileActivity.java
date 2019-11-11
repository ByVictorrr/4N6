package com.example.digitalevidence;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import com.example.digitalevidence.Helpers.DynamoHelper;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.Views.DeviceView;
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
    List<DeviceView> deviceViews;


    @Override
    @TargetApi(24) // to use java 8
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_device_info_layout);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Step 0 - declare instance of dynamohelper
        DynamoHelper dynamoHelper = new DynamoHelper(this);
        // Step 1 - init image views and text views
        deviceViews = getDeviceViews();

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
                List<Model> mobiles = (List<Model>) dynamoHelper.getModels();
                // step 3 - set text
                // Step 4 - set all urls to corresponding imageviews
                setDeviceViews(mobiles, deviceViews);
            }
        });
    }


    private void setDeviceViews(List<Model> models, List<DeviceView> deviceViews){
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            DeviceView dv;
            @Override
            public void run() {
                for(int i = 0; i < 6; i++) {
                    dv = deviceViews.get(i);
                    Picasso.get().load(models.get(i).getLink()).into(dv.getImageView());
                    dv.getTextView().setText(models.get(i).getName());
                }
            }});
    }


    private List<DeviceView> getDeviceViews(){
        List<DeviceView> deviceViews = new ArrayList<>();
        deviceViews.add((DeviceView)findViewById(R.id.DeviceView0));
        deviceViews.add((DeviceView)findViewById(R.id.DeviceView1));
        deviceViews.add((DeviceView)findViewById(R.id.DeviceView2));
        deviceViews.add((DeviceView)findViewById(R.id.DeviceView3));
        deviceViews.add((DeviceView)findViewById(R.id.DeviceView4));
        deviceViews.add((DeviceView)findViewById(R.id.DeviceView5));

        return deviceViews;
    }
    /*
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
    private List<ImageView> getImageViews(Context context, int DeviceViewSize){
        List<ImageView> imgV = new ArrayList<>();
        for (int i = 0; i < DeviceViewSize; i++){
            imgV.add(new ImageView(context));
        }
        return imgV;
    }
    // Description: returns a list of imagesViews
    private List<ImageView> getImageViews(Context context, int DeviceViewSize){
        List<ImageView> imgV = new ArrayList<>();
        for (int i = 0; i < DeviceViewSize; i++){
            imgV.add(new ImageView(context));
        }
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
        txtV.add((TextView)findViewById(R.id.textView7));
        return txtV;
    }

     */

}
