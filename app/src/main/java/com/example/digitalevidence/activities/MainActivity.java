package com.example.digitalevidence.activities;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.digitalevidence.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.app_name);

        // Buttons for Selecting Device Type
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
            i = new Intent(this, MobileActivity.class);
            startActivity(i);
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
                dialog.setContentView(R.layout.activity_help_main);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
        }
        return(super.onOptionsItemSelected(item));
    }
}