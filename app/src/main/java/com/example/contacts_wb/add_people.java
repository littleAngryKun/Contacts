package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class add_people extends AppCompatActivity {
    TextInputEditText add_name;
    FloatingActionButton add_button;
//    TextInputEditText add_phone;
    ContactViewModel contactViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        contactViewModel =new ViewModelProvider(this).get(ContactViewModel.class);
        String message = getIntent().getStringExtra("name");
        contactViewModel.DeleteById(message);
//        contactViewModel.(contact);
        add_button=findViewById(R.id.floatingActionButton);
        add_name = findViewById(R.id.add_profile_name);
//        add_phone = findViewById(R.id.add_profile_phone_number);

        if (message != null) {
            add_name.setText(message);
//            add_phone.setText(提取对于名字message的号码);
        }

        add_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                    if (b)
                        // 获取焦点时清空文本
                        add_name.setText("");
                }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = add_name.getText().toString();
                Contact contact =new Contact(Name);
                contactViewModel.insert(contact);
                Intent intent = new Intent(add_people.this,MainActivity.class);
                startActivity(intent);
            }
        });
//        add_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b)
//                    // 获取焦点时清空文本
//                    add_phone.setText("");
//
//            }
//        });
    }

    public void add_people(View view) {
        String name = add_name.getText().toString();
//        String phone = add_phone.getText().toString();




    }
}