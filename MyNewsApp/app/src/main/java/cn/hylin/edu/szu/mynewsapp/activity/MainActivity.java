package cn.hylin.edu.szu.mynewsapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.fragment.itemfragment.LiveFragment;
import cn.hylin.edu.szu.mynewsapp.fragment.itemfragment.NewsFragment;
import cn.hylin.edu.szu.mynewsapp.fragment.itemfragment.ReaderFragment;
import cn.hylin.edu.szu.mynewsapp.fragment.itemfragment.VideoFragment;
import cn.hylin.edu.szu.mynewsapp.view.NoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager viewPagerReplace;
    private RadioGroup rgItemSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerReplace = (NoScrollViewPager) findViewById(R.id.viewPagerReplace);
        rgItemSelector = (RadioGroup) findViewById(R.id.rgItemSelector);
        FragmentManager fm = getSupportFragmentManager();
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new VideoFragment());
        fragments.add(new ReaderFragment());
        fragments.add(new LiveFragment());

        viewPagerReplace.setAdapter(new FragmentPagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        rgItemSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbNews:
                        viewPagerReplace.setCurrentItem(0);
                        break;
                    case R.id.rbVideo:
                        viewPagerReplace.setCurrentItem(1);
                        break;
                    case R.id.rbRead:
                        viewPagerReplace.setCurrentItem(2);
                        break;
                    case R.id.rbLive:
                        viewPagerReplace.setCurrentItem(3);
                        break;
                }
            }
        });
    }
}
