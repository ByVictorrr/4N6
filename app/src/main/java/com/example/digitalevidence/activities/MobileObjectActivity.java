package com.example.digitalevidence.activities;

import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.digitalevidence.R;
import com.example.digitalevidence.adapters.ModelTabsAdapter;
import com.example.digitalevidence.adapters.ObjectTabsAdapter;
import com.example.digitalevidence.helpers.DynamoHelper;
import com.example.digitalevidence.models.MODEL_TYPE;
import com.example.digitalevidence.models.MobileTableDO;
import com.example.digitalevidence.models.Model;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MobileObjectActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;
    private List<List<Model>> brandObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText("HMMMMMMM????");

        // Tabs
        ObjectTabsAdapter tabsPagerAdapter = new ObjectTabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        // Utilize Items Labeled Mobile from DynamoDB
        this.dynamoHelper = new DynamoHelper(this, MODEL_TYPE.MOBILE, MobileTableDO.TABLE_NAME);
    }

    public void loadAndSet(int item_to_load){
        Thread getAll = dynamoHelper.getNItems(item_to_load);
        Thread doAll = addDataToList();
        try {
            getAll.start();
            getAll.join();
            doAll.start();
            doAll.join();
        }
        catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private Thread addDataToList(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Queue<Model> pending = dynamoHelper.getModelsPending();
                Model polled;
                int size;

                String brand = "Apple";

                while(pending.size() > 0) {
                    polled = pending.poll();
                    if ((size = brandObjects.size()) > 0 && brandObjects.get(size-1).get(0).getName().equals(polled.getName())){
                        brandObjects.get(size-1).add(polled);
                    }
                    else{
                        List<Model> newObject = new ArrayList<>();
                        brandObjects.add(newObject);
                    }
                }
            }
        });
    }

    public void setObjects(List<List<Model>> brandObjects){
        this.brandObjects = brandObjects;
    }
}