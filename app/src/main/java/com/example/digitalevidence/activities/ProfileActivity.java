package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.IdentityProvider;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.digitalevidence.R;


public class ProfileActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_profile);
        final String TAG="auth error";

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_profile);

        AWSMobileClient mc =  AWSMobileClient.getInstance();
        ProfileActivity pa = this;


        IdentityManager identityManager = new IdentityManager(getApplicationContext());
        IdentityManager.setDefaultIdentityManager(identityManager);;

        mc.initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Logged IN");
                            }
                        });
                        break;
                    case SIGNED_OUT:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Logged OUT");
                                mc.showSignIn(
                                        pa,
                                        SignInUIOptions.builder()
                                                .nextActivity(MainActivity.class)
                                                .build(),
                                        new Callback<UserStateDetails>() {
                                            @Override
                                            public void onResult(UserStateDetails result) {
                                                Log.d(TAG, "onResult: " + result.getUserState());
                                                switch (result.getUserState()){
                                                    case SIGNED_IN:
                                                        Log.i("INIT", "logged in!");
                                                        break;
                                                    case SIGNED_OUT:
                                                        Log.i(TAG, "onResult: User did not choose to sign-in");
                                                        break;
                                                    default:
                                                        AWSMobileClient.getInstance().signOut();
                                                        break;
                                                }
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                Log.e(TAG, "onError: ", e);
                                            }
                                        }
                                );

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


    }
}