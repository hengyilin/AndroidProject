package cn.hylin.edu.szu.mynewsapp.fragment.itemfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.fragment.tabfragment.ReaderTabFragment;

/**
 * Author：林恒宜 on 16-7-15 17:20
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class ReaderFragment extends Fragment {

    private String[] bookType = new String[] {"育儿","时尚","养生","两性","烹饪","修养","教育",
            "笑话","杂谈","其它"};
    private View layoutView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ReaderFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutView = inflater.inflate(R.layout.fragment_reader_layout,null,false);
        tabLayout = (TabLayout) layoutView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) layoutView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new ReaderTabFragment();
                Bundle bundle = new Bundle();
                bundle.putString("postion",String.valueOf(position + 1));
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public int getCount() {
                return bookType.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return bookType[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.WHITE,Color.RED);
        return layoutView;
    }
}
