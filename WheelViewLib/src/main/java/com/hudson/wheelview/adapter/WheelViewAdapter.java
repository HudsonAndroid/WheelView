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
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_PADDING_VIEW = 1;
    private final List<T> mDatas = new ArrayList<>();
    private final WheelViewConfig mWheelViewConfig;// non null
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
        Context context = parent.getContext();
        if(viewType == TYPE_NORMAL){
            return new ItemViewHolder<>(createItemViewByConfig(context),mOnItemClickListener);
        }else{
            return new ItemViewHolder<>(createPaddingView(context),null);
        }
    }

    private TextView createPaddingView(Context context){
        TextView textView = new TextView(context);
        int height = 0;
        if(mWheelViewConfig.isShowPaddingView()){
            //note: /2 should do before
            height = mWheelViewConfig.getPageCount() / 2 * mWheelViewConfig.getItemHeight();
        }
        textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        return textView;
    }

    private TextView createItemViewByConfig(Context context){
        TextView textView = new TextView(context);
        textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mWheelViewConfig.getItemHeight()));
        textView.setTextSize(mWheelViewConfig.getTextSize());
        textView.setTextColor(mWheelViewConfig.getTextColor());
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder<T> itemViewHolder, int i) {
        if(getItemViewType(i) == TYPE_NORMAL){
            itemViewHolder.refreshView(mDatas.get(i-1));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 2;//header + bottom + item lists
    }

    @Override
    public final int getItemViewType(int position) {
        if(position == 0 || position == mDatas.size() + 1){
            return TYPE_PADDING_VIEW;
        }else{
            return TYPE_NORMAL;
        }
    }

    public WheelViewConfig getConfig(){
        return mWheelViewConfig;
    }
}
