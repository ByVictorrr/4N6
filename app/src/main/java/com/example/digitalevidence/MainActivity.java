package com.example.digitalevidence;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button mobileBTN = (Button) findViewById(R.id.mobile_button);
        mobileBTN.setOnClickListener(this);
        Button computerBTN = (Button) findViewById(R.id.computer_button);
        computerBTN.setOnClickListener(this);
        Button storageBTN = (Button) findViewById(R.id.storage_button);
        storageBTN.setOnClickListener(this);
        Button gamingBTN = (Button) findViewById(R.id.gaming_button);
        gamingBTN.setOnClickListener(this);
        Button miscBTN = (Button) findViewById(R.id.misc_button);
        miscBTN.setOnClickListener(this);

        /*Button mobileBTN = (Button) findViewById(R.id.mobile_button);
        mobileBTN.setOnClickListener(this);
        Button computerBTN = (Button) findViewById(R.id.computer_button);
        computerBTN.setOnClickListener(this);
        Button storageBTN = (Button) findViewById(R.id.storage_button);
        storageBTN.setOnClickListener(this);
        Button gamingBTN = (Button) findViewById(R.id.gaming_button);
        gamingBTN.setOnClickListener(this);
        Button miscBTN = (Button) findViewById(R.id.misc_button);
        miscBTN.setOnClickListener(this);

        // App sync client
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        // Establishes your connection to aws and acts as an interface for your services
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.navigation_fave:
                //add the function to perform here
                return(true);
            case R.id.navigation_home:
                //add the function to perform here
                return(true);
            case R.id.app_bar_search:
                //add the function to perform here
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }


}



