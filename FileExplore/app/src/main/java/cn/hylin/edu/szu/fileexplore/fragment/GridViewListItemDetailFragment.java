package cn.hylin.edu.szu.fileexplore.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import cn.hylin.edu.szu.fileexplore.R;
import cn.hylin.edu.szu.fileexplore.adapter.MyGridViewItemListViewAdapter;
import cn.hylin.edu.szu.fileexplore.bean.Constant;
import cn.hylin.edu.szu.fileexplore.utils.GetIntentByFileName;

/**
 * Author：林恒宜 on 16-7-25 10:21
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class GridViewListItemDetailFragment extends Fragment {

    private View view;
    private ListView lvGridViewItemDetail;
    private int postion;
    private MyGridViewItemListViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            postion = Integer.parseInt(arguments.getString("position"));
        }
        Log.i("test","当期视图位置是" + postion);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_gridview_item_detail,container,false);
        lvGridViewItemDetail = (ListView) view.findViewById(R.id.lvGridViewItemDetail);
        switch (postion) {
            case 0: //音乐文件
                adapter = new MyGridViewItemListViewAdapter(getActivity(), Constant.musicList,0);
                break;
            case 1: // 视频文件
                adapter = new MyGridViewItemListViewAdapter(getActivity(), Constant.videoList,1);
                break;
            case 2: // 图片文件
                adapter = new MyGridViewItemListViewAdapter(getActivity(), Constant.pictureList,2);
                Log.i("test","Constant.pictureList的长度是" + Constant.pictureList.size());
                break;
            case 3: // 文档文件
                adapter = new MyGridViewItemListViewAdapter(getActivity(), Constant.documentList,3);
                break;
            case 4: // 安装包文件
                adapter = new MyGridViewItemListViewAdapter(getActivity(), Constant.installList,4);
                break;
            case 5: // 压缩包文件
                adapter = new MyGridViewItemListViewAdapter(getActivity(), Constant.yasuoList,5);
                break;
        }
        lvGridViewItemDetail.setAdapter(adapter);
        /**
         * 点击时出发的事件
         */
        lvGridViewItemDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = GetIntentByFileName.getIntentByFileName(adapter.getmFileList().get(position).getAbsolutePath());
                startActivity(intent);
            }
        });
        /**
         * 长按时出发的事件
         * 长按删除
         */
        lvGridViewItemDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.mipmap.icon_warming)
                        .setTitle("警告").setMessage("是否要删除文件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File file = adapter.getmFileList().get(position);
                                file.delete();
                                adapter.getmFileList().remove(file);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(),"删除成功！",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
                return false;
            }
        });
        return view;
    }
}
