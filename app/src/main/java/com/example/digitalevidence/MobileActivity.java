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
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;



public class MobileActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private ScrollView scrollView;
    public static DeviceView previousDeviceView;

    private int scrollByY; // used to get value of some how scrolled

    @Override
    @TargetApi(24) // to use java 8
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_device_info_layout);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relativeLayout = findViewById(R.id.RelativeLayout);
        scrollView = findViewById(R.id.ScrollView);

        DynamoHelper dynamoHelper = new DynamoHelper(this);


        scrollView.setOnTouchListener(new View.OnTouchListener(){
            float downY;
            int totalY;
            public boolean onTouch(View view, MotionEvent event){
                float currentY;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentY = event.getY();
                        scrollByY = (int)(downY-currentY);

                }

                return true;
            }});



        // Step 2 - get all urls from dynamo helper (for mobile objects)
        Thread getAll = dynamoHelper.getNItems(7);

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

    private Thread doAll (DynamoHelper dynamoHelper){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                List<Model> mobiles = (List<Model>) dynamoHelper.getModels();
                // step 3 - set text
                // Step 4 - set all urls to corresponding imageviews
                for(int i = 0; i < mobiles.size(); i ++ ){
                    Model model = mobiles.get(i);
                    createDeviceView(model, i);
                }
            }
        });
    }


    // Description: for dynamically creating views
    private void createDeviceView(Model model, int index){

        // Step 1 - create new device view
        DeviceView deviceView = new DeviceView(this);
        ImageView imageView = deviceView.getImageView();
        TextView textView = deviceView.getTextView();

        // Step 2-  use parent view to set device view
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // Step 3 - set the contents of the views
        deviceView.setName(model.getName());
        deviceView.setImage(model.getLink());

        // Step 4 - set id of device view
        deviceView.setId(index);

        // Step 4 - see if its passed the first deviceView
        if (index > 0){
            parms.addRule(RelativeLayout.BELOW, previousDeviceView.getId());
        }

        relativeLayout.addView(deviceView, parms);

        // Step 7 - assign device view as previous
        previousDeviceView = deviceView;

    }

}
