package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.example.digitalevidence.models.Model;
import com.example.digitalevidence.R;

import java.util.List;

public class ProfileActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_profile);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_profile);

        // Add a call to initialize AWSMobileClient
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AuthUIConfiguration config = new AuthUIConfiguration.Builder()
                        .userPools(true)  // true? show the Email and Password UI
                        .logoResId(R.drawable.apple) // Change the logo
                        .backgroundColor(R.color.colorBackground) // Change the backgroundColor
                        .isBackgroundColorFullScreen(true) // Full screen backgroundColor the backgroundColor full screenff
                        .fontFamily("sans-serif-light") // Apply sans-serif-light as the global font
                        .canCancel(true)
                        .build();

                SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(ProfileActivity.this, SignInUI.class);
                signin.login(ProfileActivity.this, ProfileActivity.class).authUIConfiguration(config).execute();

                /*if(awsStartupResult.isIdentityIdAvailable()){
                    startActivity(new Intent(AuthenticatorActivity.this, ProfileActivity.class));
                }*/
            }
        }).execute();
    }

    public void loadAndSet(int item_to_load){}

    public void setModels(Pair<String, List<Model>> brandModels){}
}