package com.hudson.wheelview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hudson.wheelview.adapter.WheelViewConfig;
import com.hudson.wheelview.listener.OnSelectChangedListener;


/**
 * completely show first visible view layout manager
 * Note:only vertical orientation
 * Created by Hudson on 2019/9/6.
 */
public class FixFirstViewLayoutManager extends LinearLayoutManager {
    private WheelView mWheelView;
    private TextView mLastCenter;
    private int mFocusPosition = 0;//maybe it's not right
    private OnSelectChangedListener mSelectChangedListener;

    FixFirstViewLayoutManager(Context context, WheelView wheelView) {
        super(context);
        mWheelView = wheelView;
    }

    @Override
    public void setOrientation(int orientation) {
        Log.w("LayoutManager","You are not allowed to change the orientation,because" +
                " wheel view only support vertical style.");
        super.setOrientation(VERTICAL);
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int position = findFirstVisibleItemPosition();
            View view = findViewByPosition(position);
            if(view != null){
                int top = view.getTop();
                int bottom = view.getBottom();
                int distanceY;
                WheelViewConfig config = mWheelView.getConfig();
                // if show top and bottom empty view,we should scroll in another way.
                if(position == 0 && config != null && config.isShowPaddingView()){
                    int itemHeight = config.getItemHeight();
                    top = top % itemHeight;
                    bottom = itemHeight + top;
                }
                distanceY = getScrollDistance(top,bottom);
                mWheelView.smoothScrollBy(0, distanceY);
            }
        }
    }

    private int getScrollDistance(int top,int bottom){
        if (Math.abs(top) >= Math.abs(bottom)) {
            return bottom;
        } else {
            return top;
        }
    }

    void focusCenterView(){
        WheelViewConfig config = mWheelView.getConfig();
        if(config == null){
            return ;
        }
        if(mLastCenter != null){
            mLastCenter.setTextColor(config.getTextColor());
        }
        int centerViewPosition = getCenterViewPosition(config);
        if(centerViewPosition > 0){
            mFocusPosition = centerViewPosition - 1;
            if(mSelectChangedListener != null && mFocusPosition != (centerViewPosition - 1)){
                mSelectChangedListener.onSelectChanged(mFocusPosition);
            }
        }
        View view = findViewByPosition(centerViewPosition);
        if(view != null){
            mLastCenter = ((TextView)view);
            mLastCenter.setTextColor(config.getFocusColor());
        }
    }

    private int getCenterViewPosition(@NonNull WheelViewConfig config){
        int resPosition = findFirstVisibleItemPosition();
        View view = findViewByPosition(resPosition);
        if(view != null){
            int top = view.getTop();
            int bottom = view.getBottom();
            if(resPosition == 0 && config.isShowPaddingView()){
                int itemHeight = config.getItemHeight();
                int offset = -top / itemHeight;
                top = top % itemHeight;
                bottom = itemHeight + top;
                resPosition += (offset + 1);//first view is empty
            }else{
                resPosition += config.getPageCount() / 2;
            }
            if(Math.abs(top) > Math.abs(bottom)){//should use next view
                resPosition ++;
            }
        }
        return resPosition;
    }

    /**
     * get the truly focus item position
     * @return position
     */
    public int getFocusPosition() {
        WheelViewConfig config = mWheelView.getConfig();
        if(config != null){
            if(!config.isShowPaddingView()){
                // in this state,wheel view cannot select the top and bottom half views.
                // however,layoutManager will save 0 or right value into {mFocusPosition} variable,
                // so we just modify the value if it's 0.
                if(mFocusPosition == 0){
                    mFocusPosition = config.getPageCount() / 2;
                }
            }
        }
        return mFocusPosition;
    }

    public void setSelectChangedListener(@NonNull OnSelectChangedListener selectChangedListener) {
        mSelectChangedListener = selectChangedListener;
    }
}
