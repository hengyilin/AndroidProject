package cn.hylin.edu.szu.fileexplore.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.fileexplore.R;
import cn.hylin.edu.szu.fileexplore.adapter.MyGridViewAdapter;
import cn.hylin.edu.szu.fileexplore.bean.Constant;
import cn.hylin.edu.szu.fileexplore.bean.GridViewItemBean;
import cn.hylin.edu.szu.fileexplore.bean.SDCardInfo;

/**
 * Author：林恒宜 on 16-7-24 20:23
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class ClassViewFragment extends Fragment {

    private String[] gridViewItemTitle = new String[] {"音乐","视频","图片","文档","安装包","压缩包"};
    private int[] gridViewItemIcon = new int[] {R.mipmap.format_music,R.mipmap.format_media,
        R.mipmap.format_picture,R.mipmap.format_document,R.mipmap.format_apk,R.mipmap.format_zip};
    private int [] gridViewItemNumber = new int[6];

//    private List<File> musicList = new ArrayList<>();
//    private List<File> videoList = new ArrayList<>();
//    private List<File> pictureList = new ArrayList<>();
//    private List<File> yasuoList = new ArrayList<>();
//    private List<File> installList = new ArrayList<>();
//    private List<File> documentList = new ArrayList<>();
   // private List<File>[] gridViewItemNumber = new List[]{musicList,videoList,pictureList,documentList,
            //installList,yasuoList};

    private List<GridViewItemBean> mItemList = new ArrayList<>();
    private View view;
    private TextView tvSDCardCap;
    private TextView tvSDCardName;
    private GridView gvClassItem;
    private MyGridViewAdapter adapter;
    private ProgressBar pbProgressBar;
    private ProgressBar pbShow;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    pbShow.setVisibility(View.GONE);
                    gridViewItemNumber[0] = Constant.musicList.size();
                    gridViewItemNumber[1] = Constant.videoList.size();
                    gridViewItemNumber[2] = Constant.pictureList.size();
                    gridViewItemNumber[3] = Constant.documentList.size();
                    gridViewItemNumber[4] = Constant.installList.size();
                    gridViewItemNumber[5] = Constant.yasuoList.size();
                    updateGridView ();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class_show_layout, container, false);

        tvSDCardCap = (TextView) view.findViewById(R.id.tvSDCardCap);
        tvSDCardName = (TextView) view.findViewById(R.id.tvSDCardName);
        gvClassItem = (GridView) view.findViewById(R.id.gvClassItem);
        pbProgressBar = (ProgressBar) view.findViewById(R.id.pbSDCardCap);
        pbShow = (ProgressBar) view.findViewById(R.id.pbShow);
        pbShow.setVisibility(View.VISIBLE);
        gvClassItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"点击了第" + position + "项" ,Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                GridViewListItemDetailFragment fragment = new GridViewListItemDetailFragment();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        bundle.putString("position","0");
//                        bundle.putSerializable("data",musicList);
                        break;
                    case 1:
                        bundle.putString("position","1");
                        break;
                    case 2:
                        bundle.putString("position","2");
                        break;
                    case 3:
                        bundle.putString("position","3");
                        break;
                    case 4:
                        bundle.putString("position","4");
                        break;
                    case 5:
                        bundle.putString("position","5");
                        break;
                }
                fragment.setArguments(bundle);
                tx.hide(ClassViewFragment.this);
                tx.add(R.id.classViewFragmentNullLayout,fragment);
                tx.addToBackStack(null);
                tx.commit();

            }
        });
        updateSDCardInfo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 0;
                //执行耗时任务
                searchSDCardForClassItem();
                mHandler.sendMessage(msg);
            }
        }).start();
        return view;
    }
    /**
     * 把SD卡信息更新到界面上
     */
    private void updateSDCardInfo() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            String name = file.getName();
            StatFs statfs = new StatFs(file.getPath());
            long blockSize = statfs.getBlockSize();
            long countSize = statfs.getBlockCount();
            long avliableSize = statfs.getAvailableBlocks();
            long totalCap = blockSize * countSize;
            long releaseCap = blockSize * avliableSize;
            SDCardInfo info = new SDCardInfo(Formatter.formatFileSize(getActivity(),totalCap),
                    Formatter.formatFileSize(getActivity(),releaseCap));
            tvSDCardName.setText(name);
            tvSDCardCap.setText(info.toString());
            int progress = (int) (((totalCap - releaseCap)*100) / totalCap);
            pbProgressBar.setProgress(progress);
        } else {
            Toast.makeText(getActivity(),"没有找到SD卡",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 查询SD卡更新分类页面
     */
    private void searchSDCardForClassItem() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File rootFile = Environment.getExternalStorageDirectory();
            searchSDCard(rootFile);
        } else {
            Toast.makeText(getActivity(),"没有找到SD卡",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 递归查询SD卡
     * @param file 文件夹或文件
     */
    private void searchSDCard(File file){
        if (file.isFile()) {
            String fileName = file.getName();
            if (fileName.endsWith(".pdf") || fileName.endsWith(".ppt") ||fileName.endsWith(".txt") ||
                    fileName.endsWith(".doc") || fileName.endsWith(".docx")||
                    fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                //文档包
                Constant.documentList.add(file);
            } else if (fileName.endsWith(".mp3") || fileName.endsWith(".m4a") ||
                    fileName.endsWith(".ape") || fileName.endsWith(".flac")) {
                //音频包
                Constant.musicList.add(file);
            } else if (fileName.endsWith(".mp4") || fileName.endsWith(".3gp")) {
                // 视频包
                Constant.videoList.add(file);
            } else if (fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".tar.gz")) {
                //压缩包
                Constant.yasuoList.add(file);
            } else if (fileName.endsWith(".jpg")) {
                //图片包
                Constant.pictureList.add(file);
            } else if (fileName.endsWith(".apk")) {
                //安装包
                Constant.installList.add(file);
            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    searchSDCard(f);
                }
            }
        }
    }
    ////////////////////////////////测试/////////////////////////////////////////////////////////
    public void FindAllAPKFile(File file) {
        if (!file.getName().startsWith(".")){
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.endsWith(".pdf") || fileName.endsWith(".txt") ||
                        fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".torrent") ||
                        fileName.endsWith(".xls") || fileName.endsWith(".xlsx") || fileName.endsWith(".ppt")) {
                    //文档包
                    Constant.documentList.add(file);
                } else if (fileName.endsWith(".mp3") || fileName.endsWith(".m4a") ||
                        fileName.endsWith(".ape") || fileName.endsWith(".flac")) {
                    //音频包
                    Constant.musicList.add(file);
                } else if (fileName.endsWith(".mp4") || fileName.endsWith(".3gp")) {
                    // 视频包
                    Constant.videoList.add(file);
                } else if (fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".tar.gz")) {
                    //压缩包
                    Constant.yasuoList.add(file);
                } else if (fileName.endsWith(".bmp") || fileName.endsWith(".jpg") || fileName.endsWith("jpeg") ||
                        fileName.endsWith(".png")) {
                    //图片包
                    Constant.pictureList.add(file);
                } else if (fileName.endsWith(".apk")) {
                    //安装包
                    Constant.installList.add(file);
                }
            } else {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File file_str : files) {
                        FindAllAPKFile(file_str);
                    }
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 更新UI
     */
    private void updateGridView() {
        int len = gridViewItemTitle.length;
        mItemList.clear();
        for (int i = 0; i < len; i ++) {
            GridViewItemBean bean = new GridViewItemBean(gridViewItemIcon[i],gridViewItemTitle[i],
                    gridViewItemNumber[i]);
            mItemList.add(bean);
        }
//        adapter.onDataChange(mItemList);
        adapter = new MyGridViewAdapter(getActivity(),mItemList);
        gvClassItem.setAdapter(adapter);
    }
}
