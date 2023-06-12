package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.contacts_wb.database.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class add_people extends AppCompatActivity {
    TextInputEditText add_name;
    FloatingActionButton add_button;
    TextInputEditText add_phone;
    TextInputEditText add_sex;

    ContactViewModel contactViewModel;
    int id;
    String name = null;
    String phoneNumber = null;
    Integer sex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");
        sex = getIntent().getIntExtra("sex", 0);
//        contactViewModel.(contact);
        add_button = findViewById(R.id.floatingActionButton);
        add_name = findViewById(R.id.add_profile_name);
        add_phone = findViewById(R.id.add_phone_number);
        add_sex = findViewById(R.id.add_sex);
//

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

                if (add_sex.getText().toString().equals("女"))
                    sex = 1;
                else
                    sex = 0;

                if(Name.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "姓名不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;//姓名为空拒绝执行
                }
//                Contact contact = new Contact(Name, Phone, sex);
//                contactViewModel.DeleteById(name);
//                contactViewModel.insert(contact);
                System.out.println(id);
                LiveData<Contact> contactLiveData = contactViewModel.getContactById(id);
// 观察 LiveData 对象并处理返回的数据
                contactLiveData.observe(add_people.this, new Observer<Contact>() {
                    @Override
                    public void onChanged(Contact contact) {
                        // 处理返回的联系人数据
                        // 在这里进行更新操作，
                        contact.setName(Name);
                        contact.setPhonenumber(Phone);
                        contact.setSex(sex);
                        // 将更新后的联系人数据保存回数据库
                        contactViewModel.updateContact(contact);
                    }
                });

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