package com.aksellk.hangman3;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startButton = (Button) findViewById(R.id.startButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.exit){
            finish();
            return true;
        }
        if (id==R.id.help) {
            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startGame(View view) {
        startActivity(new Intent(getApplicationContext(),GameActivity.class));
    }

    public void onEnglish(View view) {
        Locale locale = getResources().getConfiguration().locale;
        if(!(locale.getCountry().equals("US"))) { // if not english
            locale = new Locale("en");
            Configuration config = new Configuration();
            config.locale = locale;
            Resources resources = getBaseContext().getResources();
            resources.updateConfiguration(config,resources.getDisplayMetrics());
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void onNorwegian(View view) {
        Locale locale = getResources().getConfiguration().locale;
        if(!(locale.getCountry().equals("NO"))) { // if not english
            locale = new Locale("nb");
            Configuration config = new Configuration();
            config.locale = locale;
            Resources resources = getBaseContext().getResources();
            resources.updateConfiguration(config,resources.getDisplayMetrics());
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
