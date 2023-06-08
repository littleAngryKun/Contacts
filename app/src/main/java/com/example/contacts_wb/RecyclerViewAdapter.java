package com.example.contacts_wb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView的适配器，用于替换first版本的listView，提高效率和可扩展性
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements SectionIndexer {
    private List<SortModel> mList;
    private Context mContext;
    private boolean mIsNeedCheck;//表示是否需要显示勾选框的标志

    public RecyclerViewAdapter(Context context, List<SortModel> list){
        this.mContext = context;
        this.mList = list;
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SortModel model = mList.get(position);
        holder.tvTitle.setText(model.getName());

        //显示列表项的分组标题
        int section = getSectionForPosition(position);
        //如果当前位置是这个分组的第一个位置，就将holder中的tvLetter TextView的可见性设置为VISIBLE，'=
        // 并将其文本设置为分组标题的第一个字母。
        if (position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(model.getSortLetters());
        } else {
            holder.tvLetter.setVisibility(View.GONE);
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

    static class ViewHolder extends RecyclerView.ViewHolder{//列表项的视图
        TextView tvLetter;
        TextView tvTitle;
        ImageView checked, icon, sex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLetter = itemView.findViewById(R.id.catalog);
            tvTitle = itemView.findViewById(R.id.tv_user_item_name);
            checked = itemView.findViewById(R.id.iv_user_item_check);
            icon = itemView.findViewById(R.id.iv_user_item_icon);
            sex = itemView.findViewById(R.id.iv_user_item_sex);
        }
    }
}
