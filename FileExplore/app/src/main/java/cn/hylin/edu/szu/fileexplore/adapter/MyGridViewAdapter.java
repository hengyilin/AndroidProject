package cn.hylin.edu.szu.fileexplore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.hylin.edu.szu.fileexplore.R;
import cn.hylin.edu.szu.fileexplore.bean.GridViewItemBean;

/**
 * Author：林恒宜 on 16-7-24 22:59
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<GridViewItemBean> itemList;
    private LayoutInflater mInflater;

    public MyGridViewAdapter(Context mContext, List<GridViewItemBean> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void onDataChange(List<GridViewItemBean> list) {
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList == null ? null : itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.grid_view_item_layout,parent,false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.tvClassName = (TextView) convertView.findViewById(R.id.tvItemName);
            holder.tvItemNumber = (TextView) convertView.findViewById(R.id.tvItemNumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridViewItemBean item = itemList.get(position);
        holder.ivIcon.setBackgroundResource(item.getItemIconResId());
        holder.tvClassName.setText(item.getItemName());
        holder.tvItemNumber.setText(item.getItemNumber() + "");
        return convertView;
    }


    static class ViewHolder {
        ImageView ivIcon;
        TextView tvClassName;
        TextView tvItemNumber;
    }
}
