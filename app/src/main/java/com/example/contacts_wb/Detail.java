package com.example.contacts_wb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Detail extends AppCompatActivity {
    FloatingActionButton myfloat;
    NewContact Contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView Name_textview = findViewById(R.id.profile_name);

        String message = getIntent().getStringExtra("name");
        Name_textview.setText(message);
        myfloat = findViewById(R.id.edit_people);

        myfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupWindow popupWindow = new PopupWindow(Detail.this);

                // 设置 PopupWindow 的宽度和高度
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                // 设置 PopupWindow 的内容视图
                View contentView = LayoutInflater.from(Detail.this).inflate(R.layout.edit_people, null);
                popupWindow.setContentView(contentView);

                // 设置 PopupWindow 的背景，以便在点击外部区域时自动关闭 PopupWindow
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindow.setOutsideTouchable(true);

                // 显示 PopupWindow
                popupWindow.showAsDropDown(myfloat);
            }
        });
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


    public void change_activity(View view) {
        TextView Name_textview = findViewById(R.id.profile_name);
        Intent intent = new Intent(this, add_people.class);
        intent.putExtra("name",Name_textview.getText().toString());
        startActivity(intent);
    }

    public void delete_people(View view) {
        TextView Name_textview = findViewById(R.id.profile_name);
        String name = Name_textview.getText().toString();
        //删除数据库
        Contacts.Delete(name);
        Toast.makeText(getApplicationContext(),
                "删除成功",
                Toast.LENGTH_SHORT).show();
    }
}