package com.example.digitalevidence.Activities;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.digitalevidence.Adapters.TabsAdapter;
import com.example.digitalevidence.Helpers.DynamoHelper;
import com.example.digitalevidence.LazyLoaders.CustomAdapter;
import com.example.digitalevidence.LazyLoaders.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.Models.MODEL_TYPE;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;
import com.example.digitalevidence.databinding.ActivityMobileBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MobileActivity extends BaseActivity {

    private DynamoHelper dynamoHelper;

    //=================Lazy layout===================================\\
    // in order to enable data binding: parent xml must be layout
    private List<Model> models;
    public CustomAdapter customAdapter;
    private int loadedItems = 0;
    //=================================================\\

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabsAdapter tabsPagerAdapter = new TabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        this.dynamoHelper = new DynamoHelper(this, MODEL_TYPE.MOBILE, MobileDO.TABLE_NAME);

    }
    public void loadAndSet(int item_to_load){
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to the bottom of the list
        Thread getAll = dynamoHelper.getNItems(item_to_load);
        // Step 3 - Wait while threads are finishing and set urls images to background
        Thread doAll =  addDataToList();
        // step 4 -
        try {
            getAll.start();
            getAll.join();
            doAll.start();
            doAll.join();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }


    private Thread addDataToList(){
        //activityMobileBinding.itemProgressBar.setVisibility(View.VISIBLE);
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Queue<Model> pending = dynamoHelper.getModelsPending();
                while(pending.size() > 0) {
                    models.add(pending.poll());
                    loadedItems++;
                }
                //customAdapter.notifyDataSetChanged();
                //activityMobileBinding.itemProgressBar.setVisibility(View.GONE);
            }

        });
    }

    public void setModels(List<Model> models){
        this.models = models;
    }
}
