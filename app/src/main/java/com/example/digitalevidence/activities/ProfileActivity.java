package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.digitalevidence.R;


public class ProfileActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_profile);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_profile);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()) {
                    case SIGNED_IN:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView textView = (TextView) findViewById(R.id.test);
                                textView.setText("Logged IN");
                            }
                        });
                        break;
                    case SIGNED_OUT:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView textView = (TextView) findViewById(R.id.test);
                                textView.setText("Logged OUT");
                            }
                        });
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("INIT", e.toString());
            }
        });

        AWSMobileClient.getInstance().showSignIn(this, SignInUIOptions.builder()
            .nextActivity(MobileActivity.class)
            .backgroundColor(R.color.colorBackground)
            .canCancel(true)
            .build(),
                new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails result) {
                        Log.d("Test", "onResult: " + result.getUserState());
                        switch (result.getUserState()){
                            case SIGNED_IN:
                                Log.i("INIT", "logged in!");
                                break;
                            case SIGNED_OUT:
                                Log.i("Test", "onResult: User did not choose to sign-in");
                                break;
                            default:
                                AWSMobileClient.getInstance().signOut();
                                break;
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("TestError", "onError: ", e);
                    }
                }
        );
    }
}