package com.example.digitalevidence;

import android.annotation.TargetApi;
import android.os.Bundle;

import com.example.digitalevidence.Helpers.DynamoHelper;
import com.example.digitalevidence.Models.MobileDO;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;



public class MobileActivity extends AppCompatActivity {

    @Override
    @TargetApi(24) // to use java 8
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> urlNames; // used for getting the urls
        List<ImageView> imageViews; // used for setting

        // Step 0 - declare instance of dynamohelper
        DynamoHelper dynamoHelper = new DynamoHelper(this);
        // Step 1 - init image views
        imageViews = getImageViews();

        // Step 2 - get all urls from dynamo helper (for mobile objects)
        dynamoHelper.getAll(MobileDO.class);

        // Step 3 - Wait while threads are finishing and set urls images to background
        while(!dynamoHelper.getInThread()){

            List <MobileDO> mobiles = (List<MobileDO>)dynamoHelper.getModels();

            urlNames = mobiles.stream().map(objToLink()).collect(Collectors.toList());

            // Step 4 - set all urls to corresponding imageviews
            setImageViews(urlNames, imageViews);
            break;
        }
    }


    private void setImageViews(List<String> urlNames, List<ImageView> imageViews){
        ImageView iv;
        for(int i = 0; i < urlNames.size(); i++) {
            iv = imageViews.get(i);
            Picasso.get().load(urlNames.get(i)).into(iv);
        }
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
