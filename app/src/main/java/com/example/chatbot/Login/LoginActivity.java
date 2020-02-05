package com.example.chatbot.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbot.ChatActivity;
import com.example.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editText_phoneNumber;
    String phoneNumber;
    SharedPreferences modeSharedPreference;
    Boolean mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyTheme();

        editText_phoneNumber = findViewById(R.id.phoneNumber);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
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

        setContentView(R.layout.activity_login);
    }

    public void login(View view) {

       phoneNumber = editText_phoneNumber.getText().toString();
        if (phoneNumber.length()!=10) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginActivity.this, VerifyPhoneNumberActivity.class);
            intent.putExtra("phonenumber","+91"+phoneNumber);
            startActivity(intent);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        applyTheme();
        editText_phoneNumber = findViewById(R.id.phoneNumber);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}
