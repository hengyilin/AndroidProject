package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.LiveResponse;

/**
 * Author：林恒宜 on 16-7-16 00:08
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class LiveListViewAdapter extends BaseAdapter {
    private List<LiveResponse.Result.Data> body;
    private Context context;

    public LiveListViewAdapter(List<LiveResponse.Result.Data> body, Context context) {
        this.body = body;
        this.context = context;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public Object getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.listview_live_item_layout,null);
            viewHolder.ivLiveLogo = (ImageView) convertView.findViewById(R.id.ivLiveLogo);
            viewHolder.tvLiveTitle = (TextView) convertView.findViewById(R.id.tvLiveTitle);
            viewHolder.tvLiveImtro = (TextView) convertView.findViewById(R.id.tvLiveImtro);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvLiveTitle.setText(body.get(position).getTitle());
        viewHolder.tvLiveImtro.setText(body.get(position).getImtro());

//        viewHolder.tvNewsTitle.setText(body.get(position).getTitle());
//        viewHolder.tvNewsAuthor.setText(body.get(position).getAuthor_name());
//        viewHolder.tvNewsTime.setText(body.get(position).getDate());
//
        String imageUrls[] = body.get(position).getAlbums();
        String imageUrl = imageUrls[0].replace("\\","");
        Glide.with(context).load(imageUrl).asBitmap().into(viewHolder.ivLiveLogo);
        return convertView;
    }

    public static class ViewHolder {
        ImageView ivLiveLogo ;
        TextView tvLiveTitle ;
        TextView tvLiveImtro;
    }

}
