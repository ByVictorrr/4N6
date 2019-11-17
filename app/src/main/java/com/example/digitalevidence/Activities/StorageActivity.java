package com.example.digitalevidence.Activities;
import android.os.Bundle;
import android.widget.TextView;

import com.example.digitalevidence.R;

public class StorageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_storage);
    }
}