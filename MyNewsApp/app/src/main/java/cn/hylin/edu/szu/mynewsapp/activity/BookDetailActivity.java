package cn.hylin.edu.szu.mynewsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.show.api.ShowApiRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.adapter.MyBookDetailListAdapter;
import cn.hylin.edu.szu.mynewsapp.model.BookDetailInfo;
import cn.hylin.edu.szu.mynewsapp.model.Constants;

public class BookDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvBookDetailAuthor)
    TextView tvBookDetailAuthor;
    @BindView(R.id.tvBookDetailSummary)
    TextView tvBookDetailSummary;
    @BindView(R.id.tvBookDetailTitle)
    TextView tvBookDetailTitle;
    @BindView(R.id.lvBookDeatilItem)
    ListView lvBookDeatilItem;

    private List<BookDetailInfo.BookList> mBookList = new ArrayList<>();
    private MyBookDetailListAdapter adapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    BookDetailInfo.BookDetail bookDetails = (BookDetailInfo.BookDetail) msg.obj;
                    tvBookDetailTitle.setText(bookDetails.getName());
                    tvBookDetailAuthor.setText(bookDetails.getAuthor());
                    String summary = bookDetails.getSummary().replaceAll("\\r\\n", "");
                    tvBookDetailSummary.setText(summary);
                    BookDetailInfo.BookList[] list = bookDetails.getList();
                    for (int i = 0; i < list.length; i ++) {
                        mBookList.add(list[i]);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        adapter = new MyBookDetailListAdapter(this, mBookList);
        lvBookDeatilItem.setAdapter(adapter);
        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");
        getBookDetailByBookId(bookId);

        lvBookDeatilItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pageId = mBookList.get(position).getId();
                Intent intent1 = new Intent(BookDetailActivity.this,PagerDetailActivity.class);
                intent1.putExtra("pageId",pageId);
                startActivity(intent1);
            }
        });
    }

    private void getBookDetailByBookId(final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                String result = new ShowApiRequest("http://route.showapi.com/92-91", "21541", Constants.READER_BOOK_API_KEY)
                        .addTextPara("id", bookId).post();
                Gson gson = new Gson();
                BookDetailInfo bookDetailInfo = gson.fromJson(result, BookDetailInfo.class);
                BookDetailInfo.BookDetail bookDetails = bookDetailInfo.getShowapi_res_body().getBookDetails();
                msg.obj = bookDetails;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();

    }
}
