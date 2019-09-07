package com.hudson.wheelview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hudson.wheelview.adapter.WheelViewConfig;


/**
 * completely show first visible view layout manager
 * Note:only vertical orientation
 * Created by Hudson on 2019/9/6.
 */
public class FixFirstViewLayoutManager extends LinearLayoutManager {
    private WheelView mWheelView;
    private TextView mLastCenter;

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
            int distanceY;
            if (view != null) {
                int top = view.getTop();
                int bottom = view.getBottom();
                if (Math.abs(top) >= Math.abs(bottom)) {
                    distanceY = bottom;
                } else {
                    distanceY = top;
                }
                mWheelView.smoothScrollBy(0, distanceY);
            }
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
        View view = findViewByPosition(getCenterViewPosition() + config.getPageCount() / 2);
        if(view != null){
            mLastCenter = ((TextView)view);
            mLastCenter.setTextColor(config.getFocusColor());
        }
    }

    private int getCenterViewPosition(){
        int resPosition = findFirstVisibleItemPosition();
        View view = findViewByPosition(resPosition);
        if(view != null){
            if(Math.abs(view.getTop()) > Math.abs(view.getBottom())){//should use next view
                resPosition ++;
            }
        }
        return resPosition;
    }
}
