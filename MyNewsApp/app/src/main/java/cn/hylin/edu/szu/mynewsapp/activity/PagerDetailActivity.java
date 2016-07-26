package cn.hylin.edu.szu.mynewsapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.show.api.ShowApiRequest;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.PagerDetailInfo;

public class PagerDetailActivity extends AppCompatActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    PagerDetailInfo.BookDetail bookDetails = (PagerDetailInfo.BookDetail) msg.obj;
                    tvTitle.setText(bookDetails.getTitle());
                    String message = bookDetails.getMessage().replaceAll("\\n","")
                            .replaceAll("<p>","").replaceAll("</p>","").replaceAll(" ","");
                    tvMessage.setText(message);
                    break;
            }
        }
    };
    private TextView tvTitle;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_detail);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        String pageId = getIntent().getStringExtra("pageId");
        getPageDetailByBookId(pageId);
    }

    private void getPageDetailByBookId(final String pageId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                String result = new ShowApiRequest("http://route.showapi.com/92-32", "21541", Constants.READER_BOOK_API_KEY)
                        .addTextPara("id", pageId).post();
                Gson gson = new Gson();
                PagerDetailInfo pagerDetailInfo = gson.fromJson(result, PagerDetailInfo.class);
                PagerDetailInfo.BookDetail bookDetails = pagerDetailInfo.getShowapi_res_body().getBookDetails();
                msg.obj = bookDetails;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();

    }
}
