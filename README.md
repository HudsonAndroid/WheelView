# WheelView
基于RecyclerView构建的滚轮选择器控件。
## 示例
<img width="320" height="237" src="https://github.com/HudsonAndroid/WheelView/raw/master/images/display1.jpg"/> <img width="320" height="237" src="https://github.com/HudsonAndroid/WheelView/raw/master/images/display2.jpg"/> <img width="320" height="237" src="https://github.com/HudsonAndroid/WheelView/raw/master/images/display3.jpg"/>

## Dependency
##### 1.add repositories in your project root file build.gradle:
       maven { url 'https://jitpack.io' }
##### 2.add implementation in your module file build.gradle dependencies node:
       implementation 'com.github.HudsonAndroid:WheelView:1.0.1'
## Usage

### 1.在布局文件中声明控件
 
       <com.hudson.wheelview.WheelView  
  
          android:id="@+id/wv_first"

          android:layout_width="match_parent"

          android:layout_height="wrap_content"

          app:layout_constraintLeft_toLeftOf="parent"

          app:layout_constraintRight_toRightOf="parent"

          app:layout_constraintTop_toTopOf="parent" />
          
##### 说明：高度最好设置成wrap_content，因为内部将会根据你配置的Item的个数和高度调整WheelView的高度。
### 2.配置信息
        //config the wheel view
        WheelViewConfig config = new WheelViewConfig();
        config.setItemHeight((int) getResources().getDimension(R.dimen.height_item_view))//px
            .setFocusColor(Color.parseColor("#328FFE"))
            .setCenterLineWidth(3)//px
            .setLinePercentage(0.8f)
            .setTextColor(Color.BLACK)
            .setTextSize(14)//sp
            .setShowPaddingView(true)//default value
            .setPageCount(9);//will change to odd number
        // you can use the default config by using no param constructor
        // T must implement the method Object.toString()
        // WheelViewAdapter<T> adapter = new WheelViewAdapter<>();
        WheelViewAdapter<String> adapter = new WheelViewAdapter<>(config);
#### 属性说明：
        setItemHeight 设置每个item的高度  px
        setFocusColor 设置居中（选中）item的颜色，包括了文本和两根线条的颜色
        setCenterLineWidth 设置中央两条线条的stroke宽度  px
        setLinePercentage  设置中央两条线条的横轴的长度占据WheelView的百分比
        setTextColor  设置普通item的文本的颜色
        setTextSize   设置item的文本大小  sp
        setShowPaddingView   设置是否显示顶部和底部的paddingView，默认显示，这样头尾item便可以居中显示
        setPageCount  设置一次性显示的item个数
        
### 3.设置adapter，展示数据
      //... wheelView findViewById
      mWheelView.setWheelViewAdapter(adapter);
      adapter.refreshList(datas);//list of T
### 4.选中某个item和获取选中
       mWheelView.setSelection(20);//select 20
       mWheelView.getSelection();//get selection
### 5.附加-item的点击监听（可能作用不大）
         adapter.setOnItemClickListener(new OnItemClickListener<T>() {
            @Override
            public void onItemClick(T item,int position) {
                Log.e("MainActivity","you just click the item:"+item+",position "+position);
            }
        });
       
