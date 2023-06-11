package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.contacts_wb.*;
import com.example.contacts_wb.database.CallLog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class phone extends AppCompatActivity {
    ContactViewModel mContactViewModel;
    String call_name;
    String call_phone;
    long call_time;
    long call_end;
    int call_duration;

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
    public void phone_down(View view) {
        call_end=System.currentTimeMillis();
        call_duration=(int)(call_end-call_time)/1000;
        CallLog callLog = new CallLog(call_name,call_phone,call_duration,call_time,true);
        mContactViewModel.insert(callLog);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}