package com.example.contacts_wb;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.contacts_wb.database.Contact;
import com.example.contacts_wb.database.ContactDao;
import com.example.contacts_wb.database.ContactRoomDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements OnClickListener ,RecyclerViewAdapter.OnItemClickListener{
    private ListView sortListView;
    private BottomNavigationView bottomNavigationView;
    private SideBar sideBar; // 右边的引导
    private TextView dialog;
    private SortAdapter adapter; // 排序的适配器
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;//RecyclerView的适配器
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList; // 数据
    private List<Contact> contact;

    private PinyinComparator pinyinComparator;
    private LinearLayout xuanfuLayout; // 顶部悬浮的layout
    private ContactRoomDatabase contactRoomDatabase;
    private TextView xuanfaText, set_people; // 悬浮的文字， 和右上角的群发，增加和删除按钮
    private int lastFirstVisibleItem = -1;
    private boolean isNeedChecked; // 是否需要出现选择的按钮
//    private WordViewModel mWordViewModel;
    private ContactViewModel mContactViewModel;
    private ContactDao contactDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // 当前就在首页
                    return true;
                case R.id.navigation_history:
                    Intent Intent1 = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(Intent1);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_call:
                    Intent Intent2 = new Intent(MainActivity.this, dialing.class);
                    startActivity(Intent2);
                    overridePendingTransition(0, 0);
                    return true;
                default:
                    return false;
            }
        });

        contact = new ArrayList<>();
        //ViewModelProvider 是 Android Jetpack 架构中的一个类，用于管理 ViewModel 的生命周期
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactRoomDatabase = Room.databaseBuilder(getApplicationContext(), ContactRoomDatabase.class, "contact_database").build();//创建一个数据库实例
        contactDao = contactRoomDatabase.contactDao();//获取一个 ContactDao 的实例
        /**
         * 调用 observe() 方法来注册一个数据观察者。
         * getAllContacts() 方法返回的是一个 LiveData<List<Contact>> 对象，
         * 这个对象支持数据观察者模式，当数据库中的联系人信息发生变化时，可以自动将变化的数据发送给已经注册的观察者。
         */
        mContactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            /**
             * 当数据库中的联系人信息发生变化时，LiveData 会自动调用这个 Observer 对象的 onChanged() 方法，
             * 将变化的数据发送给已经注册的观察者。
             * @param contacts
             */
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
//                ContactArray = new String[contacts.size()];
//                for (int i = 0; i < contacts.size(); i++) {
//                    ContactArray[i] = contacts.get(i).getName();
//                }
                contact=contacts;//将查询到的联系人信息保存到 contact 变量中
                initViews();//调用 initViews() 方法来初始化应用程序的界面。
            }
        });
    }

    private void initViews() {
        characterParser = CharacterParser.getInstance();
        //获取视图中的控件对象，包括 ListView、SideBar、TextView 等。
        pinyinComparator = new PinyinComparator();
        xuanfuLayout = (LinearLayout) findViewById(R.id.top_layout);//顶部悬浮的 layout 对象
        xuanfaText = (TextView) findViewById(R.id.top_char);
        sideBar = (SideBar) findViewById(R.id.sidrbar);//SideBar 对象
        dialog = (TextView) findViewById(R.id.dialog);//提示弹框的 TextView 对象
        set_people = (TextView) findViewById(R.id.set_people); //增删按钮的绑定
        set_people.setOnClickListener(this);
        sideBar.setTextView(dialog);//设置提示弹框
        /**
         * 为右边添加触摸事件
         * 为侧边栏的OnTouchingLetterChangedListener添加了一个回调方法，当用户滑动侧边栏时，它将滑动到与该字母分组相同的第一个条目。
         */
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = recyclerViewAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                }
            }
        });

//        ContactArray = new String[contact.size()];
//        for (int i = 0; i < contact.size(); i++) {
//            ContactArray[i] = contact.get(i).getName();
//            System.out.println(ContactArray[i]);
//        }
//        ContactArray = new String[contact.size()];
//        for (int i = 0; i < contact.size(); i++) {
//            ContactArray[i] = contact.get(i).getName();
//            System.out.println(ContactArray[i]);
//        }
//        if(ContactArray.length==0)
//            System.out.println("contact长度为0");
        //将联系人列表中的数据填充到 SortModel 对象中，并根据首字母进行排序
//        SourceDateList = filledData(getResources().getStringArray(R.array.date));// 填充数据
//        SourceDateList= filledData (Final_data.getContacts());

        SourceDateList= filledData(contact);
        Collections.sort(SourceDateList, pinyinComparator);
        recyclerView = (RecyclerView) findViewById(R.id.country_lvcountry);
        recyclerViewAdapter = new RecyclerViewAdapter(this,SourceDateList,this::onItemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        //为 RecyclerView 添加一个滚动监听器，并实现了悬浮标题的效果。
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastFirstVisibleItem = -1;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                int section = recyclerViewAdapter.getSectionForPosition(firstVisibleItem);
                int nextSecPosition = recyclerViewAdapter.getPositionForSection(section + 1);

                if (firstVisibleItem != lastFirstVisibleItem) {
                    MarginLayoutParams params = (MarginLayoutParams) xuanfuLayout.getLayoutParams();
                    params.topMargin = 0;
                    xuanfuLayout.setLayoutParams(params);
                    xuanfaText.setText(String.valueOf((char) section));
                }

                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = layoutManager.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = xuanfuLayout.getHeight();
                        int bottom = childView.getBottom();
                        MarginLayoutParams params = (MarginLayoutParams) xuanfuLayout.getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            xuanfuLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                xuanfuLayout.setLayoutParams(params);
                            }
                        }
                    }
                }

                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    private List<SortModel> filledData(List<Contact> contactLiveData) {

        String [] ContactArray = new String[contactLiveData.size()];
        String [] PhoneNumber = new String[contactLiveData.size()];
        int [] sex = new int[contactLiveData.size()];
        int [] id = new int[contactLiveData.size()];
        for (int i = 0; i < contactLiveData.size(); i++) {
            ContactArray[i] = contactLiveData.get(i).getName();
            PhoneNumber[i] =contactLiveData.get(i).getPhonenumber();
            sex[i]=contactLiveData.get(i).getSex();
            id[i]=contactLiveData.get(i).getId();
        }

        List<SortModel> mSortList = new ArrayList<SortModel>();
        //遍历输入的字符串数组，并为每个字符串创建一个SortModel对象。
        for (int i = 0; i < ContactArray.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(ContactArray[i]);
            sortModel.setPhoneNumber(PhoneNumber[i]);
            sortModel.setSex(sex[i]);//先随便设置性别
            sortModel.setId(id[i]);
            /**
             * 使用characterParser对象的getSelling()方法获取每个字符串的拼音，并从拼音中提取第一个字符。
             * 然后，它将字符转换为大写字母，并将结果存储在sortString字符串变量中。
             */
            String pinyin = characterParser.getSelling(ContactArray[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            //如果是字母，将首字母存储在SortModel对象的sortLetters属性中，并将该字母转换为大写字母。
            // 否则，将#存储在sortLetters属性中，表示该字符串不以字母开头。
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }
    /**
     * 过滤数据
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_people:
                PopupWindow popupWindow = new PopupWindow(MainActivity.this);

                // 设置 PopupWindow 的宽度和高度
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                // 设置 PopupWindow 的内容视图
                View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_menu, null);
                popupWindow.setContentView(contentView);

                // 设置 PopupWindow 的背景，以便在点击外部区域时自动关闭 PopupWindow
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindow.setOutsideTouchable(true);

                // 显示 PopupWindow
                popupWindow.showAsDropDown(set_people);
                break;

            default:
                break;
        }

    }
    @Override
    public void onItemClick(View view, int position) {
        if (!isNeedChecked) {
//            Toast.makeText(getApplicationContext(),
//                    ((SortModel) recyclerViewAdapter.getItem(position)).getName(),
//                    Toast.LENGTH_SHORT).show();
            //todo 在这里添加代码，功能为跳转到详情界面
            Intent intent = new Intent(this, Detail.class);
            intent.putExtra("id",((SortModel) recyclerViewAdapter.getItem(position)).getId());
            intent.putExtra("name", ((SortModel) recyclerViewAdapter.getItem(position)).getName());
            intent.putExtra("phone", ((SortModel) recyclerViewAdapter.getItem(position)).getPhoneNumber());
            intent.putExtra("sex", ((SortModel) recyclerViewAdapter.getItem(position)).getSex());
            startActivity(intent);
        } else {
            SourceDateList.get(position).setChecked(
                    !SourceDateList.get(position).isChecked());
            recyclerViewAdapter.notifyItemChanged(position);
        }
    }
    public void add_activity(View view) {
        Intent intent = new Intent(this, add_people.class);
        intent.putExtra("is_new",true);
        startActivity(intent);

    }
    public void qunfa(View view) {
        if (isNeedChecked) {
            recyclerViewAdapter.setNeedCheck(false);
            isNeedChecked = false;
        } else {

            recyclerViewAdapter.setNeedCheck(true);
            isNeedChecked = true;
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }
}