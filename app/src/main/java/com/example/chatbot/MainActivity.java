package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    case R.id.home :
                        Toast.makeText(MainActivity.this, "Well done", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    public void test(View view) {
        Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
    }





}