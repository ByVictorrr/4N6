package com.example.digitalevidence.activities;
import android.annotation.TargetApi;
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
import com.example.digitalevidence.adapters.ModelTabsAdapter;
import com.example.digitalevidence.helpers.DynamoHelper;
import com.example.digitalevidence.models.Device;
import com.example.digitalevidence.models.Manufacturer;
import com.example.digitalevidence.models.User;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MobileDevicesActivity extends BaseActivity {
    private DynamoHelper dynamoHelper;
    private final String TABLE_NAME = "digitaln-mobilehub-2069871194-MobileBrands";
    public static final Integer LOAD_COUNT = 4;
    private List<Device> devices;

    private Set<Device> deviceList;

    @TargetApi(24)
    public void setDevices(List<Device> devices){
        this.devices = devices;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        Manufacturer manufacturer = getIntent().getParcelableExtra("BRAND");
        if(manufacturer != null) {
            deviceList = manufacturer.getDevices();
        }

        this.dynamoHelper = DynamoHelper.getInstance(this, TABLE_NAME, LOAD_COUNT);

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

    // Pass DynamoDB Helper Through
    public void LoadDevices(){
        Thread fetchDevices = dynamoHelper.fetchBrands();
        Thread displayDevices = setDevices(dynamoHelper);
        try {
            fetchDevices.start();
            fetchDevices.join();
            displayDevices.start();
            displayDevices.join();
        }
        catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private Thread setDevices(DynamoHelper helper){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Queue<Device> pending = helper.getDevicesPending();
                Device polled;
                while( pending.size() > 0 ) {
                    polled = pending.poll();
                    devices.add(polled);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()) {
            case R.id.profile:
                i = new Intent(this, MobileDevicesActivity.class);
                startActivity(i);
                return(true);
            /*case R.id.help:
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
                dialog.show();*/
        }
        return(super.onOptionsItemSelected(item));
    }
}