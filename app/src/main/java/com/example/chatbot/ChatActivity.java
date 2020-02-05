package com.example.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.chatbot.Login.LoginActivity;
import com.example.chatbot.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    SharedPreferences modeSharedPreference;
    Boolean mode;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applyTheme();
        applyToolBarChanges();
        //Intent intent = new Intent(ChatActivity.this, SettingsActivity.class);

    }

    private void applyTheme() {
        mode = false;
        modeSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        if (modeSharedPreference != null) {
            mode = modeSharedPreference.getBoolean("mode", false);
        }
        if (mode) {
            setTheme(R.style.Dark_Theme);
            Toast.makeText(this, "Dark Theme Chat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Light Theme Chat", Toast.LENGTH_SHORT).show();
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_chat);
    }

    private void applyToolBarChanges() {
        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle("ChatBot");
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        Toast.makeText(ChatActivity.this, "Well done", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.settings:
                        startActivity(new Intent(ChatActivity.this, SettingsActivity.class));
                        break;

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyTheme();
        applyToolBarChanges();
    }

    public void speechtotext(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT : if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent
                .EXTRA_RESULTS);
                Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT).show();
            }
        }
    }
}