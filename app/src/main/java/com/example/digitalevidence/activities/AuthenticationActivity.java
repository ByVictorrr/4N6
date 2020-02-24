package com.example.digitalevidence.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;

public class AuthenticationActivity extends BaseActivity {
    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.toolbar_profile);

        IdentityManager identityManager = new IdentityManager(getApplicationContext());
        IdentityManager.setDefaultIdentityManager(identityManager);;

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Intent i = new Intent(AuthenticationActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case SIGNED_OUT:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AWSMobileClient.getInstance().showSignIn(AuthenticationActivity.this,
                                        SignInUIOptions.builder()
                                                .nextActivity(MainActivity.class)
                                                .canCancel(false)
                                                .build(),
                                        new Callback<UserStateDetails>() {
                                            @Override
                                            public void onResult(UserStateDetails result) {
                                                Log.d(TAG, "onResult: " + result.getUserState());
                                                switch (result.getUserState()){
                                                    case SIGNED_IN:
                                                        Log.i(TAG, "User signed in");
                                                        break;
                                                    case SIGNED_OUT:
                                                        Log.i(TAG, "User did not choose to sign-in");
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
                Log.e(TAG, e.toString());
            }
        });
    }
}