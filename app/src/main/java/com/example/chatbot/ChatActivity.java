package com.example.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.Login.LoginActivity;
import com.example.chatbot.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    Toolbar toolbar;
    SharedPreferences modeSharedPreference;
    Boolean mode;

    EditText userInput;
    RecyclerView recyclerView;
    List<ResponseMessage> responseMessageList;
    MessageAdapter messageAdapter;
    int mSize;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = findViewById(R.id.toolbar);
        relativeLayout = findViewById(R.id.relativeLayout);
        mSize = 0;
        applyTheme();
        applyToolBarChanges();
        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(responseMessageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
    }

    public void sendMessage(View view) {
        ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
        responseMessageList.add(responseMessage);
        ResponseMessage responseMessage2 = new ResponseMessage(userInput.getText().toString(), false);
        responseMessageList.add(responseMessage2);
        messageAdapter.notifyDataSetChanged();
    }


    private void applyTheme() {
        mode = false;
        modeSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        if (modeSharedPreference != null) {
            mode = modeSharedPreference.getBoolean("mode", false);
        }
        if (mode) {
            setTheme(R.style.Dark_Theme);
            setDarkTheme();
            Toast.makeText(this, "Dark Theme Chat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Light Theme Chat", Toast.LENGTH_SHORT).show();
            setTheme(R.style.AppTheme);
            setLightTheme();
        }
    }

    private void setLightTheme() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.lightActivityBG));
    }

    private void setDarkTheme() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.darkActivityBG));
    }


    private void applyToolBarChanges() {
        setActionBar(toolbar);
        if(mSize == 0) {
            toolbar.inflateMenu(R.menu.menu);
            mSize = 3;
        }
        getActionBar().setTitle("ChatBot");
        toolbar.setTitleTextAppearance(this, R.style.toolbarFont);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu.size() == 0)
            getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
            case REQUEST_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent
                            .EXTRA_RESULTS);
                    Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT).show();
                }
        }
    }
}