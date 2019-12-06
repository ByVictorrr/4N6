package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.example.digitalevidence.adapters.ModelTabsAdapter;
import com.example.digitalevidence.helpers.DynamoHelper;
import com.example.digitalevidence.adapters.DetailedFragmentAdapter;
import com.example.digitalevidence.models.MODEL_TYPE;
import com.example.digitalevidence.models.MobileDO;
import com.example.digitalevidence.models.MobileTableDO;
import com.example.digitalevidence.models.Model;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class MobileActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;
    private List<Pair<String, List<Model>>> brandModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_mobile);

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

    public void setModels(List<Pair<String, List<Model>>> brandModels){
        this.brandModels = brandModels;
    }
}