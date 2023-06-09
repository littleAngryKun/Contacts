package com.example.contacts_wb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的适配器，用于替换first版本的listView，提高效率和可扩展性
 * 对 RecyclerView 中的数据进行适配和显示，并且支持快速滚动栏的实现。
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements SectionIndexer {
    private List<SortModel> mList;
    private List<Boolean> isExpandedList;
    private Context mContext;
    private boolean mIsNeedCheck;//表示是否需要显示勾选框的标志
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }
    public Object getItem(int position) {
        return mList.get(position);
    }
    public RecyclerViewAdapter(Context context, List<SortModel> list,OnItemClickListener onItemClickListener){
        this.mContext = context;
        this.mList = list;
        this.onItemClickListener = onItemClickListener;
        int m = list.size(); // 定义m的值为10
        isExpandedList = new ArrayList<>(m); // 创建一个初始容量为m的List
        for (int i = 0; i < m; i++) {
            isExpandedList.add(true); // 将true添加到List中
        }
    }
    public void upDateData(List<SortModel> list){//当数据发生更改时，调用此函数
        this.mList = list;
        notifyDataSetChanged();
    }
    public void setNeedCheck(boolean isNeedCheck) {
        this.mIsNeedCheck = isNeedCheck;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SortModel model = mList.get(position);
        holder.tvTitle.setText(model.getName());
        final int a = position;
        //显示列表项的分组标题
        int section = getSectionForPosition(position);
        //如果当前位置是这个分组的第一个位置，就将holder中的tvLetter TextView的可见性设置为VISIBLE，'=
        // 并将其文本设置为分组标题的第一个字母。
        if (position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(model.getSortLetters());
            holder.linearLayout_letter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int section = getSectionForPosition(a);
                    int firstPosition = getPositionForSection(section);
                    boolean isExpanded = isExpandedList.get(firstPosition);//mList.get(firstPosition).isExpanded();
                    for (int i = firstPosition; i < getItemCount(); i++) {
                        SortModel model = mList.get(i);
                        if (model.getSortLetters().charAt(0) != section) {// 如果当前项所在的分组与目标分组不同，则跳出循环
                            break;
                        }
                        isExpandedList.set(i,!isExpanded);//model.setExpanded(!isExpanded);
                    }
                    notifyDataSetChanged();
                    System.out.println("点击成功");
                }
            });
        } else {
            holder.tvLetter.setVisibility(View.GONE);
        }

        if (!isExpandedList.get(a)){
            holder.linearLayout_content.setVisibility(View.GONE);
        }else{
            holder.linearLayout_content.setVisibility(View.VISIBLE);
        }

        if (mIsNeedCheck) {
            holder.checked.setVisibility(View.VISIBLE);
            if (model.isChecked()) {
                holder.checked.setImageResource(R.drawable.round_checked);
            } else {
                holder.checked.setImageResource(R.drawable.round_unchecked);
            }
        } else {
            holder.checked.setVisibility(View.GONE);
        }
        holder.sex.setImageResource(model.getSex() == 0 ? R.drawable.boy : R.drawable.girl);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 返回一个包含列表中所有可见的节的 Object 数组。
     * 每个节对应一个快速滚动栏中的条目，并且应按照字母表顺序排列。
     */
    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    /**
     * @param sectionIndex
     * @return
     *返回给定节的第一个可见条目的位置。
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回给定位置的节的索引。
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortLetters().charAt(0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//列表项的视图
        private TextView tvLetter;
        private TextView tvTitle;
        private ImageView checked, icon, sex;
        private LinearLayout linearLayout_content;
        private LinearLayout linearLayout_letter;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener onItemClickListener) {
            super(itemView);
            linearLayout_content = itemView.findViewById(R.id.iv_user_item);
            linearLayout_letter = itemView.findViewById(R.id.linear_Letter);
            tvLetter = itemView.findViewById(R.id.catalog);
            tvTitle = itemView.findViewById(R.id.tv_user_item_name);
            checked = itemView.findViewById(R.id.iv_user_item_check);
            icon = itemView.findViewById(R.id.iv_user_item_icon);
            sex = itemView.findViewById(R.id.iv_user_item_sex);
            this.onItemClickListener = onItemClickListener;
            linearLayout_content.setOnClickListener(this);//点击
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener !=null){
                onItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
