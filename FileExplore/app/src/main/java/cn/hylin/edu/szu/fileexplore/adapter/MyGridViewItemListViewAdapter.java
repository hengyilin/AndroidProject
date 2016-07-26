package cn.hylin.edu.szu.fileexplore.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.hylin.edu.szu.fileexplore.R;

/**
 * Author：林恒宜 on 16-7-25 10:25
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyGridViewItemListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<File> mFileList;
    private LayoutInflater mInflater;
    private int itemIndex;

    public List<File> getmFileList() {
        return mFileList;
    }

    public MyGridViewItemListViewAdapter(Context mContext, List<File> mFileList, int itemIndex) {
        this.mContext = mContext;
        this.mFileList = mFileList;
        this.itemIndex = itemIndex;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mFileList == null ? 0 : mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList == null ? null : mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.i("test","mFileList的长度是" + mFileList.size());
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_file_list_item_detail,parent,false);
            mViewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivFileIconDetail);
            mViewHolder.tvName = (TextView) convertView.findViewById(R.id.tvFileNameDetail);
            mViewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvCreateDateDetail);
            mViewHolder.tvFileSize = (TextView) convertView.findViewById(R.id.tvFileSizeDetail);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        File file = mFileList.get(position);
        String fileName = file.getName();
        mViewHolder.tvName.setText(fileName);
        long date = file.lastModified();
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mViewHolder.tvDate.setText(format.format(date));
        long length = file.length();
        String fileSize = Formatter.formatFileSize(mContext, length);
        mViewHolder.tvFileSize.setText(fileSize);
        switch (itemIndex) {
            case 0: //音乐
                mViewHolder.ivIcon.setBackgroundResource(R.mipmap.format_music);
                break;
            case 1: // 视频
                mViewHolder.ivIcon.setBackgroundResource(R.mipmap.format_media);
                break;
            case 2: // 图片
                mViewHolder.ivIcon.setBackgroundResource(R.mipmap.format_picture);

                String absolutePath = mFileList.get(position).getAbsolutePath();
                Glide.with(mContext).load(new File(absolutePath)).asBitmap().into(mViewHolder.ivIcon);

                break;
            case 3: // 文档
                mViewHolder.ivIcon.setBackgroundResource(R.mipmap.format_document);
                break;
            case 4: // 安装包
                mViewHolder.ivIcon.setBackgroundResource(R.mipmap.format_apk);

                String apkPath = mFileList.get(position).getAbsolutePath();
                PackageManager pm = mContext.getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(apkPath,PackageManager.GET_ACTIVITIES);
                if (info != null) {
                    ApplicationInfo appInfo = info.applicationInfo;
                    Drawable applicationIcon = pm.getApplicationIcon(appInfo);
                    mViewHolder.ivIcon.setBackground(applicationIcon);
                }

                break;
            case 5: // 压缩包
                mViewHolder.ivIcon.setBackgroundResource(R.mipmap.format_zip);
                break;
        }
        return convertView;
    }

    static class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvDate;
        TextView tvFileSize;
    }
}
