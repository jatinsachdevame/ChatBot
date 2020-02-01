package com.example.chatbot.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.chatbot.R;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");

        if (findViewById(R.id.fragmentContainer) != null) {
            if (savedInstanceState!=null) return;

            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,new SettingsFragment()).commit();

        }

    }



}
