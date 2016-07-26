package cn.hylin.edu.szu.fileexplore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.fileexplore.R;
import cn.hylin.edu.szu.fileexplore.adapter.MyFileListAdapter;
import cn.hylin.edu.szu.fileexplore.utils.GetIntentByFileName;
import cn.hylin.edu.szu.fileexplore.utils.SearchSDCard;

/**
 * Author：林恒宜 on 16-7-24 20:10
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class LocalStoreageFragment extends Fragment{

    private ListView lvFileList;
    private List<File> mFileList = new ArrayList<>();
    private FrameLayout rootView;
    private MyFileListAdapter adapter;
    private View layoutView;
    private String newPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            newPath = arguments.getString("path");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragent_main_layout, container, false);

        lvFileList = (ListView) layoutView.findViewById(R.id.lvFile);
        adapter = new MyFileListAdapter(mFileList, getActivity());
        lvFileList.setAdapter(adapter);

        lvFileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFileList.get(position).isDirectory()) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    String path = mFileList.get(position).getAbsolutePath();
                    Bundle bundle = new Bundle();
                    bundle.putString("path",path);
                    LocalStoreageFragment fragment = new LocalStoreageFragment();
                    fragment.setArguments(bundle);
                    tx.hide(LocalStoreageFragment.this);
                    tx.add(R.id.nullLayout,fragment);
                    tx.addToBackStack(null);
                    tx.commit();
                } else {
                    Intent intent = GetIntentByFileName.getIntentByFileName(mFileList.get(position).getAbsolutePath());
                    startActivity(intent);
                }
            }
        });

        if (newPath != null) {
            mFileList = SearchSDCard.searchFileByDir(newPath);
            adapter.onDataChange(mFileList);
        }
        return layoutView;
    }
}
