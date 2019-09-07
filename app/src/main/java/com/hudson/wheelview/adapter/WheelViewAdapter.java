package com.hudson.wheelview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hudson.wheelview.listener.OnItemClickListener;
import com.hudson.wheelview.viewHolder.ItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * You should make sure the item entity has implementation the method {@link Object#toString()}
 * Created by Hudson on 2019/9/6.
 */
public class WheelViewAdapter<T> extends RecyclerView.Adapter<ItemViewHolder<T>> {
    private final List<T> mDatas = new ArrayList<>();
    private WheelViewConfig mWheelViewConfig;
    private OnItemClickListener<T> mOnItemClickListener;

    public WheelViewAdapter() {
        this(new WheelViewConfig());
    }

    public WheelViewAdapter(@NonNull WheelViewConfig config) {
        mWheelViewConfig = config;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void refreshList(List<T> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder<>(createItemViewByConfig(parent.getContext()),mOnItemClickListener);
    }

    private TextView createItemViewByConfig(Context context){
        TextView textView = new TextView(context);
        textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mWheelViewConfig.getItemHeight()));
        textView.setTextSize(mWheelViewConfig.getTextSize());
        textView.setTextColor(mWheelViewConfig.getTextColor());
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder<T> itemViewHolder, int i) {
        itemViewHolder.refreshView(mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //we just allow only one type
    @Override
    public final int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public WheelViewConfig getConfig(){
        return mWheelViewConfig;
    }
}
