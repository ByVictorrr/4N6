package com.example.digitalevidence.activities;
import android.annotation.TargetApi;
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
import com.example.digitalevidence.models.Brand;
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Queue;

public class MobileActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;
    private final String TABLE_NAME = "digitaln-mobilehub-2069871194-MobileBrands";
    private List<Brand> brands;

    @TargetApi(24)
    public void setBrands(List<Brand> brands){
        //this.brands = brands.stream().collect(Collectors.toSet());
        this.brands=brands;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        //this.brands = new HashSet<>();
        this.dynamoHelper = DynamoHelper.getInstance(this);

            // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_mobile);

        // Tabs
        ModelTabsAdapter tabsPagerAdapter = new ModelTabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

    }

    // Maybe pass dynamohelper through
    public void LoadBrands(final Integer loadNum){
        Thread fetchBrands = dynamoHelper.fetchBrands(TABLE_NAME, loadNum);
        Thread displayBrands = setBrands(dynamoHelper);
        try {
            fetchBrands.start();
            fetchBrands.join();
            displayBrands.start();
            displayBrands.join();
        }
        catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private Thread setBrands(DynamoHelper helper){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Queue<Brand> pending = helper.getBrandsPending();
                Brand polled;
                while( pending.size() > 0 ) {
                    polled = pending.poll();
                    brands.add(polled);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()) {
            case R.id.profile:
                i = new Intent(this, MobileActivity.class);
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

}