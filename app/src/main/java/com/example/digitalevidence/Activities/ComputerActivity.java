package com.example.digitalevidence.Activities;
import android.os.Bundle;

import com.example.digitalevidence.Adapters.TabsAdapter;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.widget.TextView;

public class ComputerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_computer);

        TabsAdapter tabsPagerAdapter = new TabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }

    public void loadAndSet(int item_to_load){}
}
