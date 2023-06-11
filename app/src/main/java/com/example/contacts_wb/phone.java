package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class phone extends AppCompatActivity {
    ContactViewModel mContactViewModel;
    String call_name;
    String call_phone;
    long call_time;
    long call_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        //ViewModelProvider 是 Android Jetpack 架构中的一个类，用于管理 ViewModel 的生命周期
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        call_name = getIntent().getStringExtra("call_name");
        call_phone = getIntent().getStringExtra("call_phone");
        call_time=System.currentTimeMillis();
    }


}