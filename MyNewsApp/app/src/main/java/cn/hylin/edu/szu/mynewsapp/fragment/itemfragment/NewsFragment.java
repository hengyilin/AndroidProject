package cn.hylin.edu.szu.mynewsapp.fragment.itemfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.adapter.TabViewPagerAdapter;
import cn.hylin.edu.szu.mynewsapp.fragment.tabfragment.NewsTabFragment;
import cn.hylin.edu.szu.mynewsapp.model.Constants;

/**
 * Author：林恒宜 on 16-7-15 17:15
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class NewsFragment extends Fragment {

    private String[] tabs = new String[] {"头条","社会","国内","国际","娱乐",
            "体育","军事","科技","财经","时尚"};

    private ViewPager viewPagerTab;
    private TabLayout tabLayout;
    private View layoutView;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (layoutView != null) {
            ViewGroup parent = (ViewGroup) layoutView.getParent();
            if (parent != null ) {
                parent.removeView(layoutView);
            }
            return layoutView;
        }
        layoutView = inflater.inflate(R.layout.fragment_news_layout,null,false);
        viewPagerTab = (ViewPager) layoutView.findViewById(R.id.viewPagerTab);
        tabLayout = (TabLayout) layoutView.findViewById(R.id.tabLayout);
        //初始化 Fragment
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabs.length; i ++) {
            Fragment fragment = new NewsTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", Constants.types[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        //为ViewPager设置Adapter
        viewPagerTab.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(),tabs,getActivity(),fragments));
        //绑定tab和viewPager
        tabLayout.setupWithViewPager(viewPagerTab);
        //设置分别的颜色（选择和未选择）
        tabLayout.setTabTextColors(getResources().getColor(R.color.dark_white), Color.RED);
        return layoutView;
    }
}
