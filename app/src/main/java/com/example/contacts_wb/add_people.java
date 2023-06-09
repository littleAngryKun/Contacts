package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class add_people extends AppCompatActivity {
    TextInputEditText add_name;
    TextInputEditText add_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        add_name = findViewById(R.id.add_profile_name);
        add_phone = findViewById(R.id.add_profile_phone_number);

        add_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                    if (b)
                        // 获取焦点时清空文本
                        add_name.setText("");

                }
        });

        add_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    // 获取焦点时清空文本
                    add_phone.setText("");

            }
        });
    }

    public void add_people(View view) {
        String name = add_name.getText().toString();
        String phone = add_phone.getText().toString();
    }
}