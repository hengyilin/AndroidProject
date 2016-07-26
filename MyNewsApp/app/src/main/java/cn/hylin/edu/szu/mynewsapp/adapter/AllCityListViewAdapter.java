package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.City;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.LocateState;
import cn.hylin.edu.szu.mynewsapp.utils.PinYiUtils;
import cn.hylin.edu.szu.mynewsapp.view.WrapHeightGridView;

/**
 * Author：林恒宜 on 16-7-17 15:19
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class AllCityListViewAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private List<City> cities = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private Map<String, Integer> letterIndex;
    private String[] sections;
    private int locateState = LocateState.LOCATING;
    private String locateCity;


    public AllCityListViewAdapter(List<City> cities, Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cities.add(0, new City("定位", "0"));
        this.cities.add(1, new City("热门", "1"));
        this.cities.addAll(cities);
        int size = cities.size();
        letterIndex = new HashMap<>();
        sections = new String[size];
        //分组便于后面显示
        for (int index = 0; index < size; index++) {
            String currentLetter = PinYiUtils.getFirstLetter(cities.get(index).getPingyin());//获得当前城市的首字母
            String previousLetter = index >= 1 ? PinYiUtils.getFirstLetter(cities.get(index - 1).getPingyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndex.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }

    }

    /**
     * 更新定位状态
     *
     * @param locateState 定位状态
     * @param locateCity  定位城市
     */
    public void updateLocatsState(int locateState, String locateCity) {
        this.locateState = locateState;
        this.locateCity = locateCity;
        notifyDataSetChanged();
    }

    /**
     * 获取字母的位置
     *
     * @param letter 要查询位置的字母
     * @return 返回的字母的位置
     */
    public int getLetterPostion(String letter) {
        Integer index = letterIndex.get(letter);
        return index == null ? -1 : index;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    /**
     * Returns the number of types of Views that will be created by getView.
     * 返回即将被getView（）创建的view的类型的个数
     * Each type represents a set of views that can be converted in getView.
     * 每一个类型代表一个可以在getView方法中相互转化的类型集合
     * If the adapter always returns the same type of View for all items,this method should return 1.
     * 如果适配器对于所有的条目总是返回一种类型的view，那么该方法应该返回1
     * This method will only be called when the adapter is set on the the AdapterView.
     * 这个方法将会在适配器被设置给AdapterView的时候调用
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return this.VIEW_TYPE_COUNT;
    }

    /**
     * Get the type of View that will be created by getView for the specified item.
     * 获得即将被getView（）方法创建view的类型
     *
     * @param position 条目的位置
     * @return 返回的类型
     */
    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    /**
     * 获得特点个的一个条目
     *
     * @param position 条目的位置
     * @return 返回特定的条目
     */
    @Override
    public Object getItem(int position) {
        return cities.get(position) == null ? null : cities.get(position);
    }

    /**
     * 活动的特定条目的ID
     *
     * @param position 条目的位置
     * @return 返回的特定的条目的ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回一个视图，作为列表的呈现
     *
     * @param position    条目的位置
     * @param convertView 被保留的位置
     * @param parent      容器
     * @return 返回的特定的视图
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        int type = getItemViewType(position);
        switch (type) {
            case 0://定位
                convertView = inflater.inflate(R.layout.layout_locate_city, parent, false);
                TextView tvLocate = (TextView) convertView.findViewById(R.id.tvLocate);
                ViewGroup viewGroup = (ViewGroup) convertView.findViewById(R.id.viewGroup);
                TextView tvLocateCity = (TextView) convertView.findViewById(R.id.tvLocateCity);
                //为tvLocate设定点击事件监听器，一旦检测到点击就执行点击事件的方法就是开始定位
                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //模拟定位
                        locateState = LocateState.LOCATING;
                        Timer timer = new Timer("locate");
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                locateCity = "深圳";
                                locateState = LocateState.LOCATE_SUCCESS;
                            }
                        }, 3000);
                    }
                });
                switch (locateState) {
                    case LocateState.LOCATING:
                        tvLocateCity.setText("正在定位中...");
                        break;
                    case LocateState.LOCATE_FAIL:
                        tvLocateCity.setText("定位失败...");
                        break;
                    case LocateState.LOCATE_SUCCESS:
                        //locateCity必须要在定位方法中确定他的值
                        tvLocateCity.setText(locateCity);
                        break;
                }
                break;
            case 1://热门
                convertView = inflater.inflate(R.layout.layout_hot_city, parent, false);
                WrapHeightGridView gvHotCity = (WrapHeightGridView) convertView.findViewById(R.id.gvHotCity);
                gvHotCity.setAdapter(new MyGridViewAdapter(context));
                gvHotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //返回点击的城市给原来的ACTIVITY，原来的Activity应该是启动一个Activity同事要求被启动的Activity回传数据
                        Toast.makeText(context, "你点击了：" + Constants.hotCities[position], Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2://全部
                //Log.i(Constants.DEBUG_TAG, "position = " + position);
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.city_listview_item, parent, false);
                    mViewHolder = new ViewHolder();
                    mViewHolder.tvCityPinYi = (TextView) convertView.findViewById(R.id.tvCityPinYi);
                    mViewHolder.tvCityName = (TextView) convertView.findViewById(R.id.tvCityName);
                    convertView.setTag(mViewHolder);
                } else {
                    mViewHolder = (ViewHolder) convertView.getTag();
                }
                final String city = cities.get(position).getName();
                mViewHolder.tvCityName.setText(city);
               // Log.i(Constants.DEBUG_TAG,"======================"+cities.get(position).getPingyin());
                String currentLetter = PinYiUtils.getFirstLetter(cities.get(position).getPingyin());
                //Log.i(Constants.DEBUG_TAG, "currentLetter = " + currentLetter);
                String previousLetter = PinYiUtils.getFirstLetter(cities.get(position - 1).getPingyin());
                //Log.i(Constants.DEBUG_TAG, "previousLetter = " + previousLetter);
                if (!TextUtils.equals(currentLetter, previousLetter)) {
                    mViewHolder.tvCityPinYi.setVisibility(View.VISIBLE);
                    mViewHolder.tvCityPinYi.setText(currentLetter);
                } else {
                    mViewHolder.tvCityPinYi.setVisibility(View.GONE);
                }
//                    holder.name.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (onCityClickListener != null){
//                                onCityClickListener.onCityClick(city);
//                            }
//                        }
//                    });
                break;
        }
        return convertView;
    }

    /**
     * 作为一个静态类实现资源重复利用避免内存溢出
     */
    static class ViewHolder {
        TextView tvCityPinYi;
        TextView tvCityName;
    }
}
