package com.hudson.wheelview.listener;

/**
 * Created by Hudson on 2019/9/6.
 */
public interface OnItemClickListener<T> {
    void onItemClick(T item,int position);
}
