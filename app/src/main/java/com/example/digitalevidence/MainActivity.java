package com.example.digitalevidence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mobileBTN = (Button) findViewById(R.id.mobile_button);
        mobileBTN.setOnClickListener(this);
        Button computerBTN = (Button) findViewById(R.id.computer_button);
        computerBTN.setOnClickListener(this);
        Button storageBTN = (Button) findViewById(R.id.storage_button);
        storageBTN.setOnClickListener(this);
        Button gamingBTN = (Button) findViewById(R.id.gaming_button);
        gamingBTN.setOnClickListener(this);
        Button miscBTN = (Button)findViewById(R.id.misc_button);
        miscBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){
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
            case R.id.misc_button:
                i = new Intent(this, MiscActivity.class);
                startActivity(i);
                break;

        }
    }
}



