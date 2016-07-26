package cn.hylin.edu.szu.mynewsapp.fragment.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.activity.NewsDetailActivity;
import cn.hylin.edu.szu.mynewsapp.adapter.NewsListViewAdapter;
import cn.hylin.edu.szu.mynewsapp.fragment.itemfragment.ItemBaseFragment;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.NewsResponse;
import cn.hylin.edu.szu.mynewsapp.service.NewsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.color.holo_blue_dark;

/**
 * Author：林恒宜 on 16-7-15 21:25
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description : 访问服务器然后展现数据
 */
public class NewsTabFragment extends ItemBaseFragment {
    private String mText;
    private ListView lvNewsContent;
    private NewsResponse.NewsResponseBody[] data;
    private List<NewsResponse.NewsResponseBody> list = new ArrayList<>();
    private NewsListViewAdapter adapter;
    private SwipeRefreshLayout refreshView;
    private View view;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.news_tab_layout, null);
        initView();
        initLinstener();
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView() {
        lvNewsContent = (ListView) view.findViewById(R.id.lvNewsContent);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        refreshView.setColorScheme(holo_blue_dark, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_green_light);
        adapter = new NewsListViewAdapter(list, getActivity());
        //设置适配器
        lvNewsContent.setAdapter(adapter); // 一开始列表为空所以不会有任何显示需要后面访问服务器加载数据才可以显示
        mProgressBar.setVisibility(View.VISIBLE); // 显示进度条
    }

    /**
     * 初始化监听器
     */
    private void initLinstener() {
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            //监听刷新操作
            public void onRefresh() {
                refreshView.setRefreshing(true);
                updateContent();
            }
        });

        //设置监听器
        lvNewsContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"点击了第" +  position + "条数据",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("detailsUrl", list.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    /**
     * 访问网络更新界面
     *
     */
    private void updateContent() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        NewsService newsService = retrofit.create(NewsService.class);
        Call<NewsResponse> call = newsService.getResponse(Constants.API_KEY, mText);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    NewsResponse.NewsResult result = newsResponse.getResult();
                    data = result.getData();
                    list.clear();//清空列表
                    for (int i = 0; i < data.length; i++) {
                        list.add(data[i]);
                    }
                    adapter.notifyDataSetChanged();
                    refreshView.setRefreshing(false);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "获取数据失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 懒加载是第一次进入该视图时执行的所以要清空列表
     */
    @Override
    protected void lazyLoad() {
        updateContent();
    }
}


//        AsyncTask<String, Integer, List<NewsResponse.NewsResponseBody>> myTask = new AsyncTask<String, Integer, List<NewsResponse.NewsResponseBody>>() {
//            @Override
//            protected void onPreExecute() {
//                Log.i(Constants.DEBUG_TAG, "onPreExecute");
//                super.onPreExecute();
//            }
//            @Override
//            protected List<NewsResponse.NewsResponseBody> doInBackground(String... params) {
//                Log.i(Constants.DEBUG_TAG, "doInBackground");
//                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create()).build();
//                NewsService newsService = retrofit.create(NewsService.class);
//                Call<NewsResponse> call = newsService.getResponse(Constants.API_KEY, params[0]);
//                call.enqueue(new Callback<NewsResponse>() {
//                    @Override
//                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                        if (response.isSuccessful()) {
//                            NewsResponse newsResponse = response.body();
//                            NewsResponse.NewsResult result = newsResponse.getResult();
//                            data = result.getData();
//                            for (int i = 0; i < data.length; i ++) {
//                                System.out.println("新闻标题：" + data[i].getTitle());
//                                list.add(data[i]);
//                            }
//                            for (int i = 0; i < list.size(); i++) {
//                                Log.i(Constants.DEBUG_TAG,"新闻标题为：" + list.get(i).getTitle());
//                            }
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<NewsResponse> call, Throwable t) {
//                        // Toast.makeText(context,"获取数据失败，请检查网络设置",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                return list;
//            }
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                Log.i(Constants.DEBUG_TAG, "onProgressUpdate");
//                super.onProgressUpdate(values);
//            }
//            @Override
//            protected void onPostExecute(List<NewsResponse.NewsResponseBody> newsResponseBodies) {
//                super.onPostExecute(newsResponseBodies);
//                Log.i(Constants.DEBUG_TAG, "onPostExecute");
//                Log.i(Constants.DEBUG_TAG,"开始遍历newsResponseBodies" + newsResponseBodies);
//                for (int i = 0; i < newsResponseBodies.size(); i++) {
//                    Log.i(Constants.DEBUG_TAG,"新闻标题为：" + newsResponseBodies.get(i).getTitle());
//                }
//            }
//        };
//
//        myTask.execute(mText);
