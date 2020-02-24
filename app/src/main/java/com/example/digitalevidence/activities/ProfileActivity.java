package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.widget.TextView;
import com.example.digitalevidence.R;
import com.example.digitalevidence.models.User;

public class ProfileActivity extends BaseActivity {
    String nameUser, emailUser = "not provided";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_profile);

        // Get Profile Information from Authentication
        User user = getIntent().getParcelableExtra("USER");
        if(user != null) {
            nameUser = user.getUsername();
            emailUser = user.getEmail();
        }

        // Set Values in UI
        TextView nameView = findViewById(R.id.text_name);
        nameView.setText(nameUser);
        TextView emailView = findViewById(R.id.text_email);
        emailView.setText(emailUser);
    }
}