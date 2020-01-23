package com.example.digitalevidence.activities;
import android.os.Bundle;
import android.widget.TextView;
import com.example.digitalevidence.R;


public class ProfileActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_profile);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_profile);
    }

}