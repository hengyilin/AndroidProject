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
import cn.hylin.edu.szu.mynewsapp.model.NewsResponse;

/**
 * Author：林恒宜 on 16-7-16 00:08
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class NewsListViewAdapter extends BaseAdapter {
    private List<NewsResponse.NewsResponseBody> body;
    private Context context;

    public NewsListViewAdapter(List<NewsResponse.NewsResponseBody> body, Context context) {
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
            convertView = View.inflate(context, R.layout.listview_news_item_layout,null);
            viewHolder.ivNewsLogo = (ImageView) convertView.findViewById(R.id.ivNewsLogo);
            viewHolder.tvNewsTitle = (TextView) convertView.findViewById(R.id.tvNewsTitle);
            viewHolder.tvNewsAuthor = (TextView) convertView.findViewById(R.id.tvNewsAuthor);
            viewHolder.tvNewsTime = (TextView) convertView.findViewById(R.id.tvNewsTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNewsTitle.setText(body.get(position).getTitle());
        viewHolder.tvNewsAuthor.setText(body.get(position).getAuthor_name());
        viewHolder.tvNewsTime.setText(body.get(position).getDate());

        String imageUrl = body.get(position).getThumbnail_pic_s();
        Glide.with(context).load(imageUrl).asBitmap().into(viewHolder.ivNewsLogo);


        return convertView;
    }

    public static class ViewHolder {
        ImageView ivNewsLogo ;
        TextView tvNewsTitle ;
        TextView tvNewsAuthor;
        TextView tvNewsTime ;
    }

}
