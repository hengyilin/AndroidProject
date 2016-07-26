package cn.hylin.edu.szu.fileexplore.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cn.hylin.edu.szu.fileexplore.R;
import cn.hylin.edu.szu.fileexplore.fragment.ClassViewBaseFragment;
import cn.hylin.edu.szu.fileexplore.fragment.LocalStoreageBaseFragment;
import cn.hylin.edu.szu.fileexplore.fragment.NetStoreageFragment;

public class MainActivity extends AppCompatActivity {

    private static final String[] mTabTitle = new String[]{"分类查看", "本地存储", "网盘存储"};
    private boolean isExit = false;

    private TabLayout tabLayout;
    private ViewPager viewPagerForFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPagerForFragment = (ViewPager) findViewById(R.id.viewPagerForFragment);
        viewPagerForFragment.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fm = null;
                switch (position) {
                    case 0:
                        fm = new ClassViewBaseFragment(); // 分类浏览
                        break;
                    case 1:
                        fm = new LocalStoreageBaseFragment(); // 本地存储
                        break;
                    case 2:
                        fm = new NetStoreageFragment(); // 网络存储
                        break;
                }
                return fm;
            }

            @Override
            public int getCount() {
                return mTabTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitle[position];
            }
        });
        viewPagerForFragment.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPagerForFragment);
        tabLayout.setTabTextColors(Color.WHITE, getResources().getColor(R.color.colorAccent));
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                finish();
                System.exit(0);
            } else {
                isExit = true;
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                TimerTask task = new TimerTask() {
                    public void run() {
                        isExit = false;
                    }
                };
                new Timer().schedule(task,2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }*/
}
