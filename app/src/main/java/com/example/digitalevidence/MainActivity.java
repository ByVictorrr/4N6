package com.example.digitalevidence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import android.view.Menu;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            getSupportActionBar().setTitle("My custom toolbar!");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        /*TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

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
        getMenuInflater().inflate(R.menu.menutop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                //add the function to perform here
                return(true);
            case R.id.reset:
                //add the function to perform here
                return(true);
            case R.id.about:
                //add the function to perform here
                return(true);
            case R.id.exit:
                //add the function to perform here
                return(true);
    }
        return(super.onOptionsItemSelected(item));
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



