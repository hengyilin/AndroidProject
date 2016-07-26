package cn.hylin.edu.szu.mynewsapp.fragment.itemfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.activity.SearchVideoActivity;
import cn.hylin.edu.szu.mynewsapp.activity.SelectCityActivity;
import cn.hylin.edu.szu.mynewsapp.fragment.tabfragment.VideoTabsFragment;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.VideoRecentInfoResponse;
import cn.hylin.edu.szu.mynewsapp.service.VideoRecentInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：林恒宜 on 16-7-15 17:20
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class VideoFragment extends ItemBaseFragment {

    private String[] tabs = new String[] {"正在热映","即将上映"};

    private View layoutView;
    private TextView tvSelectCity;
    private TabLayout tabLayoutClass;
    private ImageButton ibSearch;
    private ViewPager vpVideoContent;
    private List<Fragment> fragmentList;

    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_video_layout,container,false);
        initData();
        initView();
        return layoutView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        upDateRecentVideoInfo("深圳");

    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvSelectCity = (TextView) layoutView.findViewById(R.id.tvSelectCity);
        tvSelectCity.setText(Constants.cityName);
        tabLayoutClass = (TabLayout) layoutView.findViewById(R.id.tabLayoutClass);
        ibSearch = (ImageButton) layoutView.findViewById(R.id.ibSearch);
        vpVideoContent = (ViewPager) layoutView.findViewById(R.id.vpVideoContent);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < tabs.length; i ++) {
            Fragment fragment = new VideoTabsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text",tabs[i]);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        vpVideoContent.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });
        tabLayoutClass.setupWithViewPager(vpVideoContent);
        tabLayoutClass.setTabTextColors(Color.WHITE, Color.RED);
        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SelectCityActivity.class));
            }
        });

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchVideoActivity.class));
            }
        });
    }


    /**
     * 懒加载 加载数据
     */
    @Override
    protected void lazyLoad() {

    }

    private void upDateRecentVideoInfo (String cityName) {
        String name = "";
        try {
            name = URLEncoder.encode(cityName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.VIDEO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        VideoRecentInfoService infoService = retrofit.create(VideoRecentInfoService.class);
        Call<VideoRecentInfoResponse> call = infoService.getVideoRecentInfoResponse(Constants.VIDEO_API_KEY, name);
        call.enqueue(new Callback<VideoRecentInfoResponse>() {
            @Override
            public void onResponse(Call<VideoRecentInfoResponse> call, Response<VideoRecentInfoResponse> response) {
                if (response.isSuccessful()) {
                    VideoRecentInfoResponse body = response.body();
                    Log.i(Constants.DEBUG_TAG,"返回值为：" + body.getReason());
//                    Toast.makeText(getActivity(),"返回值为：" + body.getReason(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VideoRecentInfoResponse> call, Throwable t) {
                Log.i(Constants.DEBUG_TAG,"获取数据失败");
//                Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
