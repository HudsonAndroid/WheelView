package com.hudson.wheelview;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hudson.wheelview.adapter.WheelViewAdapter;
import com.hudson.wheelview.adapter.WheelViewConfig;
import com.hudson.wheelview.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WheelView wheelView = (WheelView) findViewById(R.id.wv_first);
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
        wheelView.setWheelViewAdapter(adapter);
        adapter.refreshList(datas);
        adapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item,int position) {
                Log.e("MainActivity","you just click the item:"+item+",position"+position);
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
