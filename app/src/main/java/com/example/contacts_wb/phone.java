package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.contacts_wb.*;
import com.example.contacts_wb.database.CallLog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import android.view.View;
import android.widget.TextView;

public class phone extends AppCompatActivity {
    ContactViewModel mContactViewModel;
    String call_name;
    String call_phone;
    long call_time;
    long call_end;
    int call_duration;



    private TextView timerTextView;
    private Button hangupButton;
    private CountDownTimer countDownTimer;
    private Runnable runnable;
    private long startTime;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        // 获取 UI 控件
        timerTextView = findViewById(R.id.timerTextView);
        call_time = System.currentTimeMillis();
        hangupButton = findViewById(R.id.button2);
        startTimer();//启动计时器，将通话时长输出到屏幕
        //ViewModelProvider 是 Android Jetpack 架构中的一个类，用于管理 ViewModel 的生命周期
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        Intent intent = getIntent();
        call_name = intent.getStringExtra("call_name");
        call_phone = intent.getStringExtra("call_phone");
        call_time=System.currentTimeMillis();
    }
    // 启动计时器
    private void startTimer() {
        startTime = SystemClock.elapsedRealtime();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // 计算已经过去的时间
                long timeInMilliseconds = SystemClock.elapsedRealtime() - startTime;
                // 更新计时器文本
                timerTextView.setText(formatDuration(timeInMilliseconds / 1000));
                // 继续计时
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }
    public void phone_down(View view) {
        stopTimer();
        call_end=System.currentTimeMillis();
        call_duration=(int)(call_end-call_time)/1000;
        System.out.println(call_name);
        CallLog callLog = new CallLog(call_name,call_phone,call_duration,call_time,true);
        mContactViewModel.insert(callLog);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    // 停止计时器
    private void stopTimer() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    // 格式化时间，返回形如 "00:00" 的字符串
    private String formatDuration(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds);
    }
}
