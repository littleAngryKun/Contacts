package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contacts_wb.database.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class add_people extends AppCompatActivity {
    boolean is_new ;

    TextInputEditText add_name;
    FloatingActionButton add_button;
    TextInputEditText add_phone;
    RadioGroup radioGroup;
    RadioButton radioButton0;
    RadioButton radioButton1;

    ContactViewModel contactViewModel;
    int id;
    String name = null;
    String phoneNumber = null;
    Integer sex = null;

    String name_changed;
    String phone_changed;
    int sex_changed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        is_new = getIntent().getBooleanExtra("is_new",false);
        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");
        sex = getIntent().getIntExtra("sex", 0);
//        contactViewModel.(contact);
        add_button = findViewById(R.id.floatingActionButton);
        add_name = findViewById(R.id.add_profile_name);
        add_phone = findViewById(R.id.add_phone_number);
        radioGroup = findViewById(R.id.radio_group);
        radioButton0 = findViewById(R.id.radioButton);
        radioButton1 = findViewById(R.id.radioButton2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                if (radioButton != null) {
                    String text = radioButton.getText().toString();
                    if(text.equals("男")){
                        sex_changed=0;
                        //System.out.println(text);
                    }else{
                        sex_changed=1;
                        //System.out.println(text);
                    }
                }
            }
        });
//
        if(!is_new){
            //非新建用户名，直接输入姓名
            add_name.setTextColor(Color.WHITE);
            add_phone.setTextColor(Color.WHITE);
            add_name.setText(name);
            add_phone.setText(phoneNumber);
            sex_changed=sex;
            if(sex==0){
                radioButton0.setChecked(true);
            }else{
                radioButton1.setChecked(true);
            }
        }else{
            sex_changed=0;
            radioButton0.setChecked(true);
        }
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_changed = add_name.getText().toString();
                phone_changed = add_phone.getText().toString();
                if(name_changed.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "姓名不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;//姓名为空拒绝执行
                }
//                Contact contact = new Contact(Name, Phone, sex);
//                contactViewModel.DeleteById(name);
//                contactViewModel.insert(contact);
                if(is_new){
                    Contact contact = new Contact(name_changed,phone_changed,sex_changed);
                    contactViewModel.insert(contact);
                }else{
                    LiveData<Contact> contactLiveData = contactViewModel.getContactById(id);
                    // 观察 LiveData 对象并处理返回的数据
                    contactLiveData.observe(add_people.this, new Observer<Contact>() {
                        @Override
                        public void onChanged(Contact contact) {
                            // 处理返回的联系人数据
                            // 在这里进行更新操作，
                            System.out.println(sex_changed);
                            contact.setName(name_changed);
                            contact.setPhonenumber(phone_changed);
                            contact.setSex(sex_changed);
                            // 将更新后的联系人数据保存回数据库
                            contactViewModel.updateContact(contact);
                        }
                    });
                }



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
                    add_name.setTextColor(Color.WHITE);
            }
        });
        add_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    // 获取焦点时清空文本
                    add_phone.setText("");
                    add_phone.setTextColor(Color.WHITE);
            }
        });

    }
}