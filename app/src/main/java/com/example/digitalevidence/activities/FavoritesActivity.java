package com.example.digitalevidence.activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.digitalevidence.models.Model;
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

        // Navigation (set Favorites)
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_favorites:
                        i = new Intent(getBaseContext(), FavoritesActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_search:
                        i = new Intent(getBaseContext(), SearchActivity.class);
                        startActivity(i);
                        return true;
                }
                return false;
            }
        });
    }

    public void loadAndSet(int item_to_load){}

    public void setModels(Pair<String, List<Model>> brandModels){}
}