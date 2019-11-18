package com.example.digitalevidence;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;

import com.apollographql.apollo.api.Query;
import com.example.digitalevidence.Helpers.DynamoHelper;
import com.example.digitalevidence.LazyLoaders.CustomAdapter;
import com.example.digitalevidence.LazyLoaders.EndlessRecyclerViewScrollListener;
import com.example.digitalevidence.Models.MODEL_TYPE;
import com.example.digitalevidence.Models.MobileDO;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.databinding.ActivityMobileBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class MobileActivity extends AppCompatActivity {
    private DynamoHelper dynamoHelper;

    //=================Lazy layout===================================\\
    // in order to enable data binding: parent xml must be layout
    private ActivityMobileBinding activityMobileBinding;
    private List<Model> models;
    private CustomAdapter customAdapter;
    private int loadedItems = 0;
    //=================================================\\

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_my_list);
        setContentView(R.layout.activity_mobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //=================init of Lazy layout=====================\\
        activityMobileBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobile);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityMobileBinding.RecyclerView.setLayoutManager(linearLayoutManager);
        activityMobileBinding.RecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        models = new ArrayList<>();
        customAdapter = new CustomAdapter(models);
        activityMobileBinding.RecyclerView.setAdapter(customAdapter);
        //==================================================\\


        this.dynamoHelper = new DynamoHelper(this, MODEL_TYPE.MOBILE, MobileDO.TABLE_NAME);


        loadAndSet(1);
        activityMobileBinding.RecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list=
                loadAndSet(2);
            }
        });

    }
    private void loadAndSet(int item_to_load){
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
        activityMobileBinding.itemProgressBar.setVisibility(View.VISIBLE);
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Queue<Model> pending = dynamoHelper.getModelsPending();
                while(pending.size() > 0) {
                    models.add(pending.poll());
                    loadedItems++;
                }
                customAdapter.notifyDataSetChanged();
                activityMobileBinding.itemProgressBar.setVisibility(View.GONE);
            }

        });
    }

}
