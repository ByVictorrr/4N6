package com.example.digitalevidence.Activities;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.digitalevidence.Adapters.TabsAdapter;
import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class StorageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_storage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabsAdapter tabsPagerAdapter = new TabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }

    public void loadAndSet(int item_to_load){}

    public void setModels(List<Model> models){}
}