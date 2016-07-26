package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.BookDetailInfo;

/**
 * Author：林恒宜 on 16-7-25 22:04
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyBookDetailListAdapter extends BaseAdapter {
    private Context mContext ;
    private LayoutInflater mInflater ;
    private List<BookDetailInfo.BookList> mBookList;

    public MyBookDetailListAdapter(Context mContext, List<BookDetailInfo.BookList> mBookList) {
        this.mContext = mContext;
        this.mBookList = mBookList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void onDataChange(List<BookDetailInfo.BookList> mBookList) {
        this.mBookList.clear();
        this.mBookList.addAll(mBookList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_book_detail_item,parent,false);
            mViewHolder.tvBookItem = (TextView) convertView.findViewById(R.id.tvBookItem);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tvBookItem.setText(mBookList.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        TextView tvBookItem;
    }






}
