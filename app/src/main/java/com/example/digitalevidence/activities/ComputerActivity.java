package com.example.digitalevidence.activities;
import android.os.Bundle;
import com.example.digitalevidence.adapters.TabsAdapter;
import com.example.digitalevidence.helpers.DynamoHelper;
import com.example.digitalevidence.models.MODEL_TYPE;
import com.example.digitalevidence.models.MobileDO;
import com.example.digitalevidence.models.Model;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.widget.TextView;

import java.util.List;
import java.util.Queue;

public class ComputerActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;
    private List<Model> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_computer);

        // Tabs
        TabsAdapter tabsPagerAdapter = new TabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        // Utilize Items Labeled Mobile from DynamoDB
        this.dynamoHelper = new DynamoHelper(this, MODEL_TYPE.MOBILE, MobileDO.TABLE_NAME);
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
                while(pending.size() > 0) {
                    models.add(pending.poll());
                }
            }
        });
    }

    public void setModels(List<Model> models){
        this.models = models;
    }
}
