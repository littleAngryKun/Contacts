package com.example.contacts_wb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts_wb.Adapter.CallLogAdapter;
import com.example.contacts_wb.database.CallLog;
import com.example.contacts_wb.database.Contact;
import com.example.contacts_wb.database.ContactRoomDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Detail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CallLogAdapter callLogAdapter;
    private Contact contact;
    FloatingActionButton myfloat;
    NewContact Contacts;
    ContactViewModel mContactViewModel;
    String name;
    String PhoneNumber;
    Integer sex;
    List<CallLog>callLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView Name_textview = findViewById(R.id.profile_name);

        name = getIntent().getStringExtra("name");

        //ViewModelProvider 是 Android Jetpack 架构中的一个类，用于管理 ViewModel 的生命周期
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        mContactViewModel.setContactByName(name);

        recyclerView = findViewById(R.id.CallLogList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /**
         * 调用 observe() 方法来注册一个数据观察者。
         * getAllContacts() 方法返回的是一个 LiveData<List<Contact>> 对象，
         * 这个对象支持数据观察者模式，当数据库中的联系人信息发生变化时，可以自动将变化的数据发送给已经注册的观察者。
         */
        mContactViewModel.getCallLogsByContactName(name).observe(this, new Observer<List<CallLog>>() {
            /**
             * 当数据库中的联系人信息发生变化时，LiveData 会自动调用这个 Observer 对象的 onChanged() 方法，
             * 将变化的数据发送给已经注册的观察者。
             * @param callLogs
             */
            @Override
            public void onChanged(@Nullable final List<CallLog> callLogs) {
//                ContactArray = new String[contacts.size()];
//                for (int i = 0; i < contacts.size(); i++) {
//                    ContactArray[i] = contacts.get(i).getName();
//                }
                //contact=contacts;//将查询到的联系人信息保存到 contact 变量中
                callLog=callLogs;
                if(callLog.size()!=0)
                    System.out.println(callLogs.get(0).getCallerNumber());
                callLogAdapter = new CallLogAdapter(callLogs);
                recyclerView.setAdapter(callLogAdapter);
            }
        });


        Name_textview.setText(name);
        TextView Phone_textview = findViewById(R.id.profile_phone_number);
        PhoneNumber =getIntent().getStringExtra("phone");
        sex = getIntent().getIntExtra("sex",0);
        TextView Sex_textview = findViewById(R.id.profile_sex);

        if(sex == 0)
            Sex_textview.setText("男");
        else
            Sex_textview.setText("女");


        Phone_textview.setText(PhoneNumber);
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
            TextView name = findViewById(R.id.profile_name);
            TextView phone = findViewById(R.id.profile_phone_number);

            String call_name = name.getText().toString();
            String call_phone = phone.getText().toString();
            Intent intent = new Intent(this, phone.class);

            intent.putExtra("call_name",call_name);
            intent.putExtra("call_phone",call_phone);

            startActivity(intent);
    }

    public void mail(View view) {
        //短信页面
        Intent intent = new Intent(this, mail.class);
        startActivity(intent);
    }


    public void change_activity(View view) {
        Intent intent = new Intent(this, add_people.class);
        intent.putExtra("name",name);
        intent.putExtra("phone",PhoneNumber);
        intent.putExtra("sex",sex);
        startActivity(intent);
    }

    public void delete_people(View view) {
        TextView Name_textview = findViewById(R.id.profile_name);
        String name = Name_textview.getText().toString();
        //删除数据库
        mContactViewModel.DeleteById(name);

        Toast.makeText(getApplicationContext(),
                "删除成功",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}