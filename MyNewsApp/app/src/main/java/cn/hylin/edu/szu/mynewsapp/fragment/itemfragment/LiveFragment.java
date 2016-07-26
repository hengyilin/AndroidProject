package cn.hylin.edu.szu.mynewsapp.fragment.itemfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.adapter.TabViewPagerAdapter;
import cn.hylin.edu.szu.mynewsapp.fragment.tabfragment.LiveTabFragment;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.LiveResponse;
import cn.hylin.edu.szu.mynewsapp.service.LiveService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：林恒宜 on 16-7-15 17:21
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class LiveFragment extends Fragment {

    private String[] menus = new String[] {"家常菜","快手菜","创意菜","素菜","凉菜","烘焙","面食","汤类","调味"};
    private ViewPager viewPagerTab;
    private TabLayout tabLayout;
    private View layoutView;
    private List<Fragment> liveTabFragments = new ArrayList<>();

    public LiveFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutView != null) {
            ViewGroup parent = (ViewGroup) layoutView.getParent();
            if (parent != null) {
                parent.removeView(layoutView);
            }
            return layoutView;
        }
        layoutView = inflater.inflate(R.layout.fragment_live_layout,null,false);
        viewPagerTab = (ViewPager) layoutView.findViewById(R.id.viewPagerTab);
        tabLayout = (TabLayout) layoutView.findViewById(R.id.tabLayout);
        //初始化 Fragment
        for (int i = 0; i < menus.length; i ++) {
            Fragment fragment = new LiveTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", Constants.ids[i]);//把要查询的关键字传递过去
            fragment.setArguments(bundle);
            liveTabFragments.add(fragment);
        }
        //为ViewPager设置Adapter
        viewPagerTab.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(),menus,getActivity(), liveTabFragments));
        //绑定tab和viewPager
        tabLayout.setupWithViewPager(viewPagerTab);
        //设置分别的颜色（选择和未选择）
        tabLayout.setTabTextColors(getResources().getColor(R.color.dark_white), Color.RED);
        return layoutView;
    }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateContent() throws UnsupportedEncodingException {
        Log.i(Constants.DEBUG_TAG,"updateContent(执行了)");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        LiveService liveService = retrofit.create(LiveService.class);
        Call<LiveResponse> call = liveService.getResponse(Constants.LIVE_API_KEY,"1", String.valueOf(30));
        call.enqueue(new Callback<LiveResponse>() {
            @Override
            public void onResponse(Call<LiveResponse> call, Response<LiveResponse> response) {
                if (response.isSuccessful()) {
                    //mProgressBar.setVisibility(View.INVISIBLE);
                    LiveResponse liveResponse = response.body();
                    Log.i(Constants.DEBUG_TAG,"resultcode = " + liveResponse.getResultcode());
                    Log.i(Constants.DEBUG_TAG,"reason = " + URLDecoder.decode(liveResponse.getReason()));
                    Log.i(Constants.DEBUG_TAG,"error_code = " + liveResponse.getError_code());
                    LiveResponse.Result result = liveResponse.getResult();
                    if (result == null) {
                        Log.i(Constants.DEBUG_TAG,"result是null");
                        return ;
                    }
                    LiveResponse.Result.Data[] datas = result.getData();
                    //dataList.clear();//清空列表
                    for (int i = 0; i < datas.length; i++) {
                        Log.i(Constants.DEBUG_TAG,"标题是：" + datas[i].getTitle());
                        //dataList.add(datas[i]);
                    }
//                    refreshView.setRefreshing(false);
//                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
//                    liveListViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LiveResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "获取数据失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
