package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.City;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.utils.PinYiUtils;

/**
 * Author：林恒宜 on 16-7-17 20:07
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class ResultCityAdapter extends BaseAdapter {
    private Context context;
    private List<City> cities;
    private LayoutInflater inflater;

    public ResultCityAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
        inflater = LayoutInflater.from(context);

        if (this.cities == null) {
            this.cities = new ArrayList<>();
        }
    }

    public void dataChange(List<City> cities) {
        if (this.cities == null ) {
            this.cities = cities;
        } else {
            this.cities.clear();
            this.cities.addAll(cities);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.city_listview_item,parent,false);
            viewHolder.tvCityPinYi = (TextView) convertView.findViewById(R.id.tvCityPinYi);
            viewHolder.tvCityName = (TextView) convertView.findViewById(R.id.tvCityName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String name = cities.get(position).getName();
        String currentLetter = PinYiUtils.getFirstLetter(cities.get(position).getPingyin());
        if (position >= 1) {
            String previousLetter = PinYiUtils.getFirstLetter(cities.get(position - 1).getPingyin());
            if (TextUtils.equals(currentLetter,previousLetter)) {
                viewHolder.tvCityPinYi.setVisibility(View.GONE);
            }else {
                viewHolder.tvCityPinYi.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.tvCityPinYi.setText(currentLetter);
        viewHolder.tvCityName.setText(name);

        return convertView;
    }

    public static class ViewHolder {
        TextView tvCityPinYi;
        TextView tvCityName;
    }



}
