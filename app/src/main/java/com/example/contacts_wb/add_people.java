package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class add_people extends AppCompatActivity {
    TextInputEditText add_name;
    FloatingActionButton add_button;
    TextInputEditText add_phone;
    TextInputEditText add_sex;

    ContactViewModel contactViewModel;
    String name = null;
    String phoneNumber = null;
    Integer sex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        name = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");
        sex = getIntent().getIntExtra("sex", 0);

        contactViewModel.DeleteById(name);
//        contactViewModel.(contact);
        add_button = findViewById(R.id.floatingActionButton);
        add_name = findViewById(R.id.add_profile_name);
        add_phone = findViewById(R.id.add_phone_number);
        add_sex = findViewById(R.id.add_sex);


        add_name.setText(name);
        add_phone.setText(phoneNumber);
        if (sex == 0)
            add_sex.setText("男");
        else
            add_sex.setText("女");

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sex;
                String Name = add_name.getText().toString();
                String Phone = add_phone.getText().toString();

                if (add_sex.getText().toString().equals("男"))
                    sex = 0;
                else
                    sex = 1;

                Contact contact = new Contact(Name, Phone, sex);
                contactViewModel.insert(contact);
                Intent intent = new Intent(add_people.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
        add_sex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    // 获取焦点时清空文本
                    add_sex.setText("");
            }
        });
    }
}