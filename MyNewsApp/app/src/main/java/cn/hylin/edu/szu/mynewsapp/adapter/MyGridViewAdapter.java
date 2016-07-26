package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;

/**
 * Author：林恒宜 on 16-7-17 17:24
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> mCities ;
    private LayoutInflater inflater;

    public MyGridViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mCities = new ArrayList<>();
        mCities.add("北京");
        mCities.add("上海");
        mCities.add("广州");
        mCities.add("深圳");
        mCities.add("杭州");
        mCities.add("南京");
        mCities.add("天津");
        mCities.add("武汉");
        mCities.add("重庆");

    }

    @Override
    public int getCount() {
        return mCities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotCityViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_gridview_item, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tvHotCityItem);
            convertView.setTag(holder);
        }else{
            holder = (HotCityViewHolder) convertView.getTag();
        }
        holder.name.setText(mCities.get(position));
        return convertView;
    }

    public static class HotCityViewHolder{
        TextView name;
    }
}
