package com.example.contacts_wb;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author J 实现了 基础的功   能 感谢这位博主，该源码是基于它修改的。
 *         http://blog.csdn.net/xiaanming/article/details/12684155
 *
 */
public class MainActivity extends Activity implements OnClickListener ,RecyclerViewAdapter.OnItemClickListener{
    private ListView sortListView;
    private SideBar sideBar; // 右边的引导
    private TextView dialog;
    private SortAdapter adapter; // 排序的适配器

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;//RecyclerView的适配器

    private CharacterParser characterParser;
    private List<SortModel> SourceDateList; // 数据

    private PinyinComparator pinyinComparator;
    private LinearLayout xuanfuLayout; // 顶部悬浮的layout
    private TextView xuanfaText, QunFa, set_people; // 悬浮的文字， 和右上角的群发，增加和删除按钮
    private int lastFirstVisibleItem = -1;
    private boolean isNeedChecked; // 是否需要出现选择的按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();



    }

    private void initViews() {
        characterParser = CharacterParser.getInstance();
        //获取视图中的控件对象，包括 ListView、SideBar、TextView 等。
        pinyinComparator = new PinyinComparator();
        xuanfuLayout = (LinearLayout) findViewById(R.id.top_layout);//顶部悬浮的 layout 对象
        xuanfaText = (TextView) findViewById(R.id.top_char);
        sideBar = (SideBar) findViewById(R.id.sidrbar);//SideBar 对象
        dialog = (TextView) findViewById(R.id.dialog);//提示弹框的 TextView 对象
        QunFa = (TextView) findViewById(R.id.qunfa);//群发按钮对象
        set_people = (TextView) findViewById(R.id.set_people); //增删按钮的绑定
        QunFa.setOnClickListener(this);
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

        //recyclerView = (RecyclerView) findViewById(R.id.country_lvcountry);
        // 为列表视图的OnItemClickListener添加了一个回调方法。
        // 当用户单击列表项时，如果isNeedChecked为false，则显示一个包含所选项名称的短暂提示。
        // 否则，将更改所选项的状态，并调用notifyDataSetChanged()方法来刷新列表项的状态。
//        sortListView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                if (!isNeedChecked) {
//                    Toast.makeText(getApplication(),
//                            ((SortModel) adapter.getItem(position)).getName(),
//                            Toast.LENGTH_SHORT).show();
//                    //todo 在这里要修改为跳转到详情界面
//                } else {
//                    SourceDateList.get(position).setChecked(
//                            !SourceDateList.get(position).isChecked());
//                    adapter.notifyDataSetChanged(); // 这样写效率很低， 以后可以改成
//                    // RecycleView 直接notify
//                    // item的状态
//                }
//
//            }
//
//        });


        //将联系人列表中的数据填充到 SortModel 对象中，并根据首字母进行排序
        SourceDateList = filledData(getResources().getStringArray(R.array.date));// 填充数据

        Collections.sort(SourceDateList, pinyinComparator);
        //adapter = new SortAdapter(this, SourceDateList);
        //sortListView.setAdapter(adapter);

        recyclerView = (RecyclerView) findViewById(R.id.country_lvcountry);
        recyclerViewAdapter = new RecyclerViewAdapter(this,SourceDateList,this::onItemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

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
//        recyclerViewAdapter.setOnItemClickListener(new recyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (!isNeedChecked) {
//                    Toast.makeText(getApplicationContext(),
//                            ((SortModel) adapter.getItem(position)).getName(),
//                            Toast.LENGTH_SHORT).show();
//                    //todo 在这里要修改为跳转到详情界面
//                } else {
//                    SourceDateList.get(position).setChecked(
//                            !SourceDateList.get(position).isChecked());
//                    adapter.notifyItemChanged(position);
//                }
//            }
//        });
        /**
         * 设置滚动监听， 实时跟新悬浮的字母的值
         */
//        sortListView.setOnScrollListener(new OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                int section = adapter.getSectionForPosition(firstVisibleItem);
//                int nextSecPosition = adapter
//                        .getPositionForSection(section + 1);
//                if (firstVisibleItem != lastFirstVisibleItem) {
//                    MarginLayoutParams params = (MarginLayoutParams) xuanfuLayout
//                            .getLayoutParams();
//                    params.topMargin = 0;
//                    xuanfuLayout.setLayoutParams(params);
//                    xuanfaText.setText(String.valueOf((char) section));
//                }
//                if (nextSecPosition == firstVisibleItem + 1) {
//                    View childView = view.getChildAt(0);
//                    if (childView != null) {
//                        int titleHeight = xuanfuLayout.getHeight();
//                        int bottom = childView.getBottom();
//                        MarginLayoutParams params = (MarginLayoutParams) xuanfuLayout
//                                .getLayoutParams();
//                        if (bottom < titleHeight) {
//                            float pushedDistance = bottom - titleHeight;
//                            params.topMargin = (int) pushedDistance;
//                            xuanfuLayout.setLayoutParams(params);
//                        } else {
//                            if (params.topMargin != 0) {
//                                params.topMargin = 0;
//                                xuanfuLayout.setLayoutParams(params);
//                            }
//                        }
//                    }
//                }
//                lastFirstVisibleItem = firstVisibleItem;
//            }
//        });

    }

    /**
     * 填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        //遍历输入的字符串数组，并为每个字符串创建一个SortModel对象。
        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            sortModel.setSex(i % 2);//先随便设置性别
            /**
             * 使用characterParser对象的getSelling()方法获取每个字符串的拼音，并从拼音中提取第一个字符。
             * 然后，它将字符转换为大写字母，并将结果存储在sortString字符串变量中。
             */
            String pinyin = characterParser.getSelling(date[i]);
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
            case R.id.qunfa:
                if (isNeedChecked) {
                    recyclerViewAdapter.setNeedCheck(false);
                    isNeedChecked = false;
                } else {

                    recyclerViewAdapter.setNeedCheck(true);
                    isNeedChecked = true;
                }
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case R.id.set_people:
                PopupWindow popupWindow = new PopupWindow(MainActivity.this);

                // 设置 PopupWindow 的宽度和高度
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                // 设置 PopupWindow 的内容视图
                View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_menu, null);
                popupWindow.setContentView(contentView);

                // 设置 PopupWindow 的背景，以便在点击外部区域时自动关闭 PopupWindow
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
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
            Toast.makeText(getApplicationContext(),
                    ((SortModel) recyclerViewAdapter.getItem(position)).getName(),
                    Toast.LENGTH_SHORT).show();
            //todo 在这里添加代码，功能为跳转到详情界面
            Intent intent = new Intent(this, Detail.class);
            intent.putExtra("name", ((SortModel) recyclerViewAdapter.getItem(position)).getName());
            startActivity(intent);
        } else {
            SourceDateList.get(position).setChecked(
                    !SourceDateList.get(position).isChecked());
            recyclerViewAdapter.notifyItemChanged(position);
        }
    }

    public void add_activity(View view) {
        Intent intent = new Intent(this, add_people.class);
        startActivity(intent);
    }
}