package com.hudson.wheelview.adapter;

import android.graphics.Color;

/**
 * Created by Hudson on 2019/9/6.
 */
public class WheelViewConfig {
    private static final int DEFAULT_ITEM_HEIGHT = 80;//px
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final float DEFAULT_TEXT_SIZE = 14;//sp
    private static final int DEFAULT_PAGE_COUNT = 5;
    private static final float DEFAULT_LINE_PERCENTAGE = 0.6f;//default center line percentage
    private static final int DEFAULT_FOCUS_COLOR = Color.RED;
    private static final float DEFAULT_CENTER_LINE_WIDTH = 3;//px

    private int mItemHeight = DEFAULT_ITEM_HEIGHT;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private float mTextSize = DEFAULT_TEXT_SIZE;
    private int mPageCount = DEFAULT_PAGE_COUNT;
    private float mLinePercentage = DEFAULT_LINE_PERCENTAGE;
    private int mFocusColor = DEFAULT_FOCUS_COLOR;
    private float mCenterLineWidth = DEFAULT_CENTER_LINE_WIDTH;

    public WheelViewConfig setItemHeight(int itemHeight) {
        if(itemHeight > 0){
            mItemHeight = itemHeight;
        }
        return this;
    }

    /**
     * config the normal item view text color
     * @param textColor the color you want to set
     * @return config
     */
    public WheelViewConfig setTextColor(int textColor) {
        mTextColor = textColor;
        return this;
    }

    public WheelViewConfig setTextSize(float textSize) {
        if(textSize > 0){
            mTextSize = textSize;
        }
        return this;
    }

    /**
     * config visible item's count
     * Note: WheelView will change your input to odd number.
     * @param pageCount count
     * @return
     */
    public WheelViewConfig setPageCount(int pageCount) {
        if(pageCount > 0){
            mPageCount = pageCount;
            mPageCount = pageCount / 2 * 2 + 1;//must odd number
        }
        return this;
    }

    public WheelViewConfig setLinePercentage(float linePercentage) {
        if(linePercentage > 0){
            mLinePercentage = linePercentage;
        }
        return this;
    }

    public WheelViewConfig setCenterLineWidth(float centerLineWidth) {
        if(centerLineWidth > 0){
            mCenterLineWidth = centerLineWidth;
        }
        return this;
    }

    public WheelViewConfig setFocusColor(int focusColor) {
        mFocusColor = focusColor;
        return this;
    }

    public float getCenterLineWidth() {
        return mCenterLineWidth;
    }

    public int getItemHeight() {
        return mItemHeight;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public float getLinePercentage() {
        return mLinePercentage;
    }

    public int getFocusColor() {
        return mFocusColor;
    }
}
