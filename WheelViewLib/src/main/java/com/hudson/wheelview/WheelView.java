package com.hudson.wheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.hudson.wheelview.adapter.WheelViewAdapter;
import com.hudson.wheelview.adapter.WheelViewConfig;
import com.hudson.wheelview.listener.OnSelectChangedListener;


/**
 * Created by hudson on 2019/9/6.
 */
public class WheelView extends RecyclerView implements WheelViewAdapter.OnDataListChangeListener {
    private WheelViewConfig mConfig;
    private FixFirstViewLayoutManager mLayoutManager;
    private int mFirstLineTop = -1;
    private Paint mLinePaint;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLayoutManager = new FixFirstViewLayoutManager(context, this);
        setLayoutManager(mLayoutManager);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                mLayoutManager.focusCenterView(false);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setWheelViewAdapter(WheelViewAdapter adapter) {
        adapter.setOnDataListChangeListener(this);
        mConfig = adapter.getConfig();
        setAdapter(adapter);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if(layoutParams != null){
            layoutParams.height = mConfig.getItemHeight() * mConfig.getPageCount();
        }
        mFirstLineTop = mConfig.getPageCount() / 2 * mConfig.getItemHeight();
        //config paint line
        mLinePaint.setColor(mConfig.getFocusColor());
        mLinePaint.setStrokeWidth(mConfig.getCenterLineWidth());
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mConfig != null){
            getLayoutParams().height = mConfig.getItemHeight() * mConfig.getPageCount();
        }
    }

    @Nullable
    public WheelViewConfig getConfig() {
        return mConfig;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mFirstLineTop != -1){
            //draw two center lines
            int width = getWidth();
            int offset = (int) (width * (1 - mConfig.getLinePercentage())/2 + 0.5f);
            canvas.drawLine(offset,mFirstLineTop, width - offset,mFirstLineTop,mLinePaint);
            int top = mFirstLineTop + mConfig.getItemHeight();
            canvas.drawLine(offset,top, width - offset,top,mLinePaint);
        }
    }

    public void setSelection(int position){
        if(mConfig == null){
            Log.w("WheelView","You should set wheel view adapter before you do it");
            return ;
        }
        int halfCount = mConfig.getPageCount() / 2;
        int size = 0;
        Adapter adapter = getAdapter();
        if(adapter != null){
            size = adapter.getItemCount() - 2;//top + bottom
        }
        if((position < halfCount || position >= (size - halfCount)) && !mConfig.isShowPaddingView()){
            Log.w("WheelView","Sorry,wheel view cannot select the position in this state," +
                    "you can only select the position from "+halfCount + " to "+(size - halfCount - 1));
        }

        //scroll to target position and fit the top
        if(position < halfCount && mConfig.isShowPaddingView()){
            mLayoutManager.scrollToPositionWithOffset(0,-position * mConfig.getItemHeight());
        }else{
            mLayoutManager.scrollToPositionWithOffset(position - halfCount + 1,0);
        }
    }

    /**
     * get the select view's position
     * @return position
     */
    public int getSelection(){
        return mLayoutManager.getFocusPosition();
    }

    public void setOnSelectChangedListener(OnSelectChangedListener listener){
        mLayoutManager.setSelectChangedListener(listener);
    }

    @Override
    public void onDataListChange() {
        mLayoutManager.focusCenterView(true);
    }
}
