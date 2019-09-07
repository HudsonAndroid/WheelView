package com.hudson.wheelview.viewHolder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hudson.wheelview.listener.OnItemClickListener;


/**
 * Created by Hudson on 2019/9/6.
 */
public class ItemViewHolder<T> extends RecyclerView.ViewHolder {
    private TextView mContent;
    private OnItemClickListener<T> mOnItemClickListener;

    public ItemViewHolder(@NonNull TextView itemView, @Nullable OnItemClickListener<T> listener) {
        super(itemView);
        mContent = itemView;
        mOnItemClickListener = listener;
    }

    public void refreshView(final T data) {
        mContent.setText(data.toString());
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(data,getAdapterPosition());
                }
            }
        });
    }
}
