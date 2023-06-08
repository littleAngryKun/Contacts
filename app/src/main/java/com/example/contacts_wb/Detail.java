package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView Name_textview = findViewById(R.id.profile_name);
        String message = getIntent().getStringExtra("name");
        Name_textview.setText(message);
    }

    public void call(View view) {
        //电话页面
        Intent intent = new Intent(this, phone.class);
        startActivity(intent);
    }

    public void mail(View view) {
        //短信页面
        Intent intent = new Intent(this, mail.class);
        startActivity(intent);
    }
}