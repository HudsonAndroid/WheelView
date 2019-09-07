package com.hudson.wheelview;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hudson.wheelview.adapter.WheelViewAdapter;
import com.hudson.wheelview.adapter.WheelViewConfig;
import com.hudson.wheelview.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WheelView mWheelView;
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWheelView = (WheelView) findViewById(R.id.wv_first);
        mEditText = (EditText) findViewById(R.id.et_selection);
        //config the wheel view
        WheelViewConfig config = new WheelViewConfig();
        config.setItemHeight((int) getResources().getDimension(R.dimen.height_item_view))
            .setFocusColor(Color.parseColor("#328FFE"))
            .setCenterLineWidth(3)
            .setLinePercentage(0.8f)
            .setPageCount(9);

        WheelViewAdapter<String> adapter = new WheelViewAdapter<>(config);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("我是第" + i + "个数据");
        }
        mWheelView.setWheelViewAdapter(adapter);
        adapter.refreshList(datas);
        adapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item,int position) {
                Log.e("MainActivity","you just click the item:"+item+",position"+position);
            }
        });
        mWheelView.setSelection(20);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void selectItem(View view) {
        String text = mEditText.getText().toString();
        if(!TextUtils.isEmpty(text)){
            mWheelView.setSelection(Integer.valueOf(text));
        }
    }
}
