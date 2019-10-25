package com.example.digitalevidence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.view.Menu;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity/*  implements View.OnClickListener*/ {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("My Toolbar");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Name Here");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        -------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        Button mobileBTN = (Button) findViewById(R.id.mobile_button);
        mobileBTN.setOnClickListener(this);
        Button computerBTN = (Button) findViewById(R.id.computer_button);
        computerBTN.setOnClickListener(this);
        Button storageBTN = (Button) findViewById(R.id.storage_button);
        storageBTN.setOnClickListener(this);
        Button gamingBTN = (Button) findViewById(R.id.gaming_button);
        gamingBTN.setOnClickListener(this);
        Button miscBTN = (Button)findViewById(R.id.misc_button);
        miscBTN.setOnClickListener(this);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /*@Override
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
    }*/
}



