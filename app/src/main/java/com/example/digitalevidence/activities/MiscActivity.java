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
import com.example.digitalevidence.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MiscActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_misc);

        // Tabs
        ModelTabsAdapter tabsPagerAdapter = new ModelTabsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        // Utilize Items Labeled Mobile from DynamoDB
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

}