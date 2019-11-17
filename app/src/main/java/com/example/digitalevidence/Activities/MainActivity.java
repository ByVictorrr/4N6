package com.example.digitalevidence.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.example.digitalevidence.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.app_name);

        // Establishes your connection to aws and acts as an interface for your services
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        Button mobileBTN = findViewById(R.id.mobile_button);
        mobileBTN.setOnClickListener(this);
        Button computerBTN = findViewById(R.id.computer_button);
        computerBTN.setOnClickListener(this);
        Button storageBTN = findViewById(R.id.storage_button);
        storageBTN.setOnClickListener(this);
        Button gamingBTN = findViewById(R.id.gaming_button);
        gamingBTN.setOnClickListener(this);
        Button miscBTN = findViewById(R.id.misc_button);
        miscBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.mobile_button:
                i = new Intent(this, MobileActivity.class);
                startActivity(i);
                break;
            case R.id.computer_button:
                i = new Intent(this, ComputerActivity.class);
                startActivity(i);
                break;
            case R.id.storage_button:
                i = new Intent(this, StorageActivity.class);
                startActivity(i);
                break;
            case R.id.gaming_button:
                i = new Intent(this, GamingActivity.class);
                startActivity(i);
                break;
            case R.id.misc_button:
                i = new Intent(this, MiscActivity.class);
                startActivity(i);
                break;
        }
    }
}