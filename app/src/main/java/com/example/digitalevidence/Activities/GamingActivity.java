package com.example.digitalevidence.Activities;
import android.os.Bundle;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;

import com.example.digitalevidence.Adapters.TabsAdapter;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class GamingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_gaming);

        TabsAdapter tabsPagerAdapter = new TabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }

    public void loadAndSet(int item_to_load){}
    public void setModels(List<Model> models){}

}