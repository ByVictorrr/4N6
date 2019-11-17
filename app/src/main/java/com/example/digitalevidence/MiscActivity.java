package com.example.digitalevidence;
import android.os.Bundle;
import android.widget.TextView;

public class MiscActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_misc);
    }
}