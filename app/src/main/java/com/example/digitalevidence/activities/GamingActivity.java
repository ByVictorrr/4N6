package com.example.digitalevidence.activities;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.example.digitalevidence.adapters.ModelTabsAdapter;
import com.example.digitalevidence.helpers.DynamoHelper;
import com.example.digitalevidence.models.MODEL_TYPE;
import com.example.digitalevidence.models.MobileTableDO;
import com.example.digitalevidence.models.Model;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GamingActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;
    private List<Pair<String, List<Model>>> brandModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_gaming);

        // Tabs
        ModelTabsAdapter tabsPagerAdapter = new ModelTabsAdapter(this, getSupportFragmentManager());
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
                while(pending.size() > 0) {
                    polled = pending.poll();
                    // Case 1 - one of prev pulls was the same brand
                    if ((size = brandModels.size()) > 0 && brandModels.get(size-1).first.equals(polled.getBrand())){
                        brandModels.get(size-1).second.add(polled);
                    }else{
                        List<Model> models = new ArrayList<>();
                        models.add(polled);
                        String brand =  polled.getBrand();
                        Pair<String, List<Model>> newPair = new Pair<>(brand, models);
                        brandModels.add(newPair);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()) {
            case R.id.profile:
                i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                return(true);
            case R.id.help:
                Dialog dialog = new Dialog(this){
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        // Tap anywhere to close
                        this.dismiss();
                        return true;
                    }
                };
                dialog.setContentView(R.layout.activity_help_models);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
        }
        return(super.onOptionsItemSelected(item));
    }

    public void setModels(List<Pair<String, List<Model>>> brandModels){
        this.brandModels = brandModels;
    }
}