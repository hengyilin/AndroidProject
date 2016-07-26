package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.BookListInfo;

/**
 * Author：林恒宜 on 16-7-25 19:07
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyReaderTabListViewAdapter extends BaseAdapter {
    private BookListInfo mBooksInfos ;
    private Context mContext;
    private LayoutInflater mInflater;
    private BookListInfo.ResBody.BookList[] bookList;

    public MyReaderTabListViewAdapter(Context mContext, BookListInfo mBooksInfos) {
        this.mContext = mContext;
        this.mBooksInfos = mBooksInfos;
        mInflater = LayoutInflater.from(mContext);
        bookList = mBooksInfos.getShowapi_res_body().getBookList();
    }

    public void onDataChange(BookListInfo mBooksInfos) {
        bookList = mBooksInfos.getShowapi_res_body().getBookList();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bookList == null ? 0 : bookList.length;
    }

    @Override
    public Object getItem(int position) {
        return bookList == null ? null : bookList[position];
    }

    @Override
    public long getItemId(int position) {
        return bookList == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null ) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.read_item_layout, parent, false);
            mViewHolder.tvBookTitle = (TextView) convertView.findViewById(R.id.tvBookTitle);
            mViewHolder.tvBookSummary = (TextView) convertView.findViewById(R.id.tvBookSummary);
            mViewHolder.tvBookAuthor = (TextView) convertView.findViewById(R.id.tvBookAuthor);
            mViewHolder.tvBookFrom = (TextView) convertView.findViewById(R.id.tvBookFrom);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tvBookTitle.setText(bookList[position].getName());
        mViewHolder.tvBookFrom.setText(bookList[position].getFrom());
        mViewHolder.tvBookAuthor.setText(bookList[position].getAuthor());
        String summary = bookList[position].getSummary();
        summary = summary.replaceAll("\\r\\n","");
        mViewHolder.tvBookSummary.setText(summary);
        return convertView;
    }
    static class  ViewHolder {
        TextView tvBookTitle;
        TextView tvBookSummary;
        TextView tvBookAuthor;
        TextView tvBookFrom;
    }
}
