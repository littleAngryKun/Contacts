package com.example.contacts_wb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.contacts_wb.Adapter.CallLogAdapter;
import com.example.contacts_wb.database.CallLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.invoke.MethodHandle;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CallLogAdapter callLogAdapter;
    ContactViewModel mContactViewModel;
    private BottomNavigationView bottomNavigationView;
    private
    List<CallLog> callLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent dashboardIntent = new Intent(HistoryActivity.this, MainActivity.class);
                    startActivity(dashboardIntent);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_history:

                    return true;
                case R.id.navigation_call:
                    return true;
                default:
                    return false;
            }
        });
        recyclerView = findViewById(R.id.history_recyclerview);

        /**
         * 调用 observe() 方法来注册一个数据观察者。
         * getAllContacts() 方法返回的是一个 LiveData<List<Contact>> 对象，
         * 这个对象支持数据观察者模式，当数据库中的联系人信息发生变化时，可以自动将变化的数据发送给已经注册的观察者。
         */
        mContactViewModel.getAllCallLogs().observe(this, new Observer<List<CallLog>>() {
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
                if(callLogs.size()!=0)
                    System.out.println(callLogs.get(0).getCallerNumber());
                callLogAdapter = new CallLogAdapter(callLog);
                recyclerView.setAdapter(callLogAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
            }
        });
    }
}