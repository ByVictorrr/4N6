package com.example.digitalevidence.activities;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
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
                dialog.setContentView(R.layout.activity_help_objects);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
        }
        return(super.onOptionsItemSelected(item));
    }

    public Manufacturer getBrand() {
        return brand;
    }
}