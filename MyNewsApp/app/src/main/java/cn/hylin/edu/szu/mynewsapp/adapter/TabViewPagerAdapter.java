package cn.hylin.edu.szu.mynewsapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author：林恒宜 on 16-7-15 18:30
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private Context context;
    private List<Fragment> fragments ;


    public TabViewPagerAdapter(FragmentManager fm,String[] titles,Context context,List<Fragment>fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
