package com.example.digitalevidence.activities;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.digitalevidence.models.User;

public class AuthenticationActivity extends BaseActivity {
    final String TAG = this.getClass().getSimpleName();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IdentityManager identityManager = new IdentityManager(getApplicationContext());
        IdentityManager.setDefaultIdentityManager(identityManager);;

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        try {
                            String username = AWSMobileClient.getInstance().getUserAttributes().get("given_name");
                            String email = AWSMobileClient.getInstance().getUserAttributes().get("email");
                            user = new User(username, email);
                        }
                        catch (Exception e) {
                            user = new User("not provided", "not provided");
                            Log.e("TEST", "User Details Error: ", e);
                        }
                        Intent i = new Intent(AuthenticationActivity.this, ProfileActivity.class);
                        i.putExtra("USER", user);
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
                                                        try {
                                                            String username = AWSMobileClient.getInstance().getUserAttributes().get("given_name");
                                                            String email = AWSMobileClient.getInstance().getUserAttributes().get("email");
                                                            user = new User(username, email);
                                                        }
                                                        catch (Exception e) {
                                                            user = new User("not provided", "not provided");
                                                            Log.e("TEST", "User Details Error: ", e);
                                                        }
                                                        Intent i = new Intent(AuthenticationActivity.this, ProfileActivity.class);
                                                        i.putExtra("USER", user);
                                                        startActivity(i);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}