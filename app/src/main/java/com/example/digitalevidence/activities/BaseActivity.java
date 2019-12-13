package com.example.digitalevidence.activities;
import android.content.Intent;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.digitalevidence.models.Model;
import com.example.digitalevidence.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

abstract public class BaseActivity extends AppCompatActivity {
    Menu menu;
    String className = this.getClass().getSimpleName();

    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        // Back Button if not a Navigation Screen
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (!className.equals("MainActivity")) {
                if (!className.equals("FavoritesActivity")) {
                    if (!className.equals("SearchActivity")) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
            }
        }

        // Navigation (set Home)
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()) {
            case R.id.profile:
                i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                return(true);
            /*case R.id.help:
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                return(true);*/
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    abstract public void loadAndSet(int item_to_load);
    public void setModels(List<Pair<String, List<Model>>> brandModels){}
    public void setlistLists(List<List<Model>> brandObjects){}
}