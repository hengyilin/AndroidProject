package cn.hylin.edu.szu.mynewsapp.fragment.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.activity.LiveDetailsActivity;
import cn.hylin.edu.szu.mynewsapp.adapter.LiveListViewAdapter;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.LiveResponse;
import cn.hylin.edu.szu.mynewsapp.service.LiveService;
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
public class LiveTabFragment extends Fragment {
    private String mType;
    private ListView lvLiveContent;
    private LiveResponse.Result.Data[] datas;//得到返回的json中的Data数组实例
    private List<LiveResponse.Result.Data> dataList = new ArrayList<>();//把数组变成列表便于操作
    private LiveListViewAdapter liveListViewAdapter;
    private SwipeRefreshLayout refreshView;
    private View view;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.live_tab_layout,null);
        initView();
        updateContent();
        initLinstener();
        return view;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        lvLiveContent = (ListView) view.findViewById(R.id.lvLiveContent);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        refreshView.setColorScheme(holo_blue_dark, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_green_light);

        liveListViewAdapter = new LiveListViewAdapter(dataList, getActivity());//这里初始化传进的列表的空列表不会
                                                                // 有什么显示后面从服务器加载数据后要通知更新
        //设置适配器
        lvLiveContent.setAdapter(liveListViewAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化事件监听器
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
        lvLiveContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LiveDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",dataList.get(position).getTitle());//标题
                bundle.putString("title",dataList.get(position).getTitle());//标题
                bundle.putString("tags",dataList.get(position).getTags());// 功能
                bundle.putString("imtro",dataList.get(position).getImtro()); // 介绍
                bundle.putString("ingredients",dataList.get(position).getIngredients());// 原料
                bundle.putString("burden",dataList.get(position).getBurden());//配料
                LiveResponse.Result.Data.Steps[] steps = dataList.get(position).getSteps();
                List<LiveResponse.Result.Data.Steps> stepsList = new ArrayList<>();
                for (int i = 0; i < steps.length; i ++) {
                    stepsList.add(steps[i]);
                }
                bundle.putSerializable("steps", (Serializable) stepsList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 访问网络更新界面
     */
    private void updateContent() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        LiveService liveService = retrofit.create(LiveService.class);
        Call<LiveResponse> call = liveService.getResponse(Constants.LIVE_API_KEY, mType, String.valueOf(30));
        call.enqueue(new Callback<LiveResponse>() {
            @Override
            public void onResponse(Call<LiveResponse> call, Response<LiveResponse> response) {
                if (response.isSuccessful()) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    LiveResponse liveResponse = response.body();
                    LiveResponse.Result result = liveResponse.getResult();
                    if (result == null) {
                        return ;
                    }
                    datas = result.getData();
                    dataList.clear();//清空列表
                    for (int i = 0; i < datas.length; i++) {
                        dataList.add(datas[i]);
                    }
                    refreshView.setRefreshing(false);
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    liveListViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LiveResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "获取数据失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
