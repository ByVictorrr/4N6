package com.example.digitalevidence.Activities;
import android.os.Bundle;
import android.widget.TextView;

import com.example.digitalevidence.Models.Model;
import com.example.digitalevidence.R;

import java.util.List;

public class HelpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_help);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_help);
    }

    public void loadAndSet(int item_to_load){}
    public void setModels(List<Model> models){}

}