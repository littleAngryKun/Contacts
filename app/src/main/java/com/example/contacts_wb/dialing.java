package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dialing extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialing);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent Intent1 = new Intent(dialing.this, MainActivity.class);
                    startActivity(Intent1);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_history:
                    Intent Intent2 = new Intent(dialing.this, HistoryActivity.class);
                    startActivity(Intent2);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_call:
                    // 当前就在首页
                    return true;
                default:
                    return false;
            }
        });
    }
}