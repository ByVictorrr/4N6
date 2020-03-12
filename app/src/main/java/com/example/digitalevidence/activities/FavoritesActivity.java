package com.example.digitalevidence.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.digitalevidence.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FavoritesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_favorites);

        // Toolbar
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_favorites);

    }
}