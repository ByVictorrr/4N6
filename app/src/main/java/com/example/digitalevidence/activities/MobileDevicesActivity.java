package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.example.digitalevidence.R;
import com.example.digitalevidence.adapters.ObjectTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class MobileDevicesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        // Obtain Selected Brand from Click
        String selectedBrand = getIntent().getStringExtra("SELECTEDBRAND");

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(selectedBrand);

        // Tabs
        ObjectTabsAdapter tabsPagerAdapter = new ObjectTabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }
}