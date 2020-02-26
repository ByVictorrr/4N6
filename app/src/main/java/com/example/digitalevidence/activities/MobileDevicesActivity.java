package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.example.digitalevidence.R;
import com.example.digitalevidence.adapters.ObjectTabsAdapter;
import com.example.digitalevidence.models.Manufacturer;
import com.google.android.material.tabs.TabLayout;

public class MobileDevicesActivity extends BaseActivity {
    Manufacturer brand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        // Obtain Selected Brand from Click
        brand = (Manufacturer)getIntent().getSerializableExtra("BRAND_DEVICES");

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(brand.getName());

        // Tabs
        ObjectTabsAdapter tabsPagerAdapter = new ObjectTabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }
    public Manufacturer getBrand(){return brand;}

}