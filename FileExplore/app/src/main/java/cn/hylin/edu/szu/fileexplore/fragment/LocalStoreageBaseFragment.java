package cn.hylin.edu.szu.fileexplore.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hylin.edu.szu.fileexplore.R;

/**
 * Author：林恒宜 on 16-7-24 21:32
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class LocalStoreageBaseFragment extends Fragment {
    private View layoutView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.localstoreage_fragment_null_layout, container, false);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        LocalStoreageFragment fragment = new LocalStoreageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", Environment.getExternalStorageDirectory().getAbsolutePath());
        fragment.setArguments(bundle);
        tx.replace(R.id.nullLayout, fragment);
        tx.commit();
        return layoutView;
    }
}
