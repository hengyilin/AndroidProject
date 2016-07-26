package cn.hylin.edu.szu.mynewsapp.fragment.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.show.api.ShowApiRequest;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.activity.BookDetailActivity;
import cn.hylin.edu.szu.mynewsapp.adapter.MyReaderTabListViewAdapter;
import cn.hylin.edu.szu.mynewsapp.fragment.itemfragment.ItemBaseFragment;
import cn.hylin.edu.szu.mynewsapp.model.BookListInfo;
import cn.hylin.edu.szu.mynewsapp.model.Constants;

import static android.R.color.holo_blue_dark;

/**
 * Author：林恒宜 on 16-7-25 17:01
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class ReaderTabFragment extends ItemBaseFragment {

    private String postion;
    private View view;
    private ListView mListView;
    private BookListInfo mBookListInfos;
    private MyReaderTabListViewAdapter adapter;
    private ProgressBar progressBar3;
    private SwipeRefreshLayout refreshView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    refreshView.setRefreshing(false);
                    progressBar3.setVisibility(View.GONE);
                    if (adapter == null) {
                        adapter = new MyReaderTabListViewAdapter(getActivity(), mBookListInfos);
                        mListView.setAdapter(adapter);
                    } else {
                        adapter.onDataChange(mBookListInfos);
                    }
                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            postion = arguments.getString("postion");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null ) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.layout_reader_tab_fragment, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        refreshView.setColorScheme(holo_blue_dark, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_green_light);
        progressBar3 = (ProgressBar) view.findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.VISIBLE);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.setRefreshing(true);
                refreshView();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookListInfo.ResBody.BookList[] bookList = mBookListInfos.getShowapi_res_body().getBookList();
                String bookId = bookList[position].getId();
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("bookId",bookId);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    protected void lazyLoad() {
        refreshView();
    }

    private void refreshView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                String result = new ShowApiRequest("http://route.showapi.com/92-92", "21541", Constants.READER_BOOK_API_KEY)
                        .addTextPara("limit", "20").addTextPara("id", postion).post();
                Gson gson = new Gson();
                mBookListInfos = gson.fromJson(result, BookListInfo.class);
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}








































/**
 * Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.READER_BASE_URL)
 .addConverterFactory(GsonConverterFactory.create()).build();
 ReaderBookSearchService service = retrofit.create(ReaderBookSearchService.class);
 Call<List<BookListInfo>> call = service.getBookListById(postion, String.valueOf(1), String.valueOf(21541), Constants.READER_BOOK_API_KEY);
 call.enqueue(new Callback<List<BookListInfo>>() {
@Override
public void onResponse(Call<List<BookListInfo>> call, Response<List<BookListInfo>> response) {
if (response.isSuccessful()) {
List<BookListInfo> mBookListInfos = response.body();
BookListInfo.ResBody.BookList[] bookList = mBookListInfos.get(0).getShowapi_res_body().getBookList();
String summary = bookList[0].getSummary();
Log.i(Constants.DEBUG_TAG,summary);
}
}

@Override
public void onFailure(Call<List<BookListInfo>> call, Throwable t) {
Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
}
});
 */
