package cn.hylin.edu.szu.fileexplore.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.hylin.edu.szu.fileexplore.R;

/**
 * Author：林恒宜 on 16-7-24 14:32
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyFileListAdapter extends BaseAdapter {
    private List<File> mFileList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MyFileListAdapter(List<File> mFileList, Context mContext) {
        this.mFileList = mFileList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void onDataChange(List<File> list) {
        mFileList.clear();
        mFileList.addAll(list);
        notifyDataSetChanged();
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
        if (mFileList == null){
            return null;
        }
        FileListViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_file_list_item,parent,false);
            viewHolder = new FileListViewHolder();
            viewHolder.ivFileIcon = (ImageView) convertView.findViewById(R.id.ivFileIcon);
            viewHolder.tvFileCreateTime = (TextView) convertView.findViewById(R.id.tvCreateDate);
            viewHolder.tvFileName = (TextView) convertView.findViewById(R.id.tvFileName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FileListViewHolder) convertView.getTag();
        }
        if (mFileList.get(position).isDirectory()) {
            viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_folder);
        } else if (mFileList.get(position).isFile()){
            String name = mFileList.get(position).getName();
            if (name.endsWith(".pdf")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_pdf);
            } else if (name.endsWith(".txt") ) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_text);
            } else if ( name.endsWith(".doc") || name.endsWith(".docx")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_word);
            } else if (name.endsWith(".mp3") || name.endsWith(".m4a")|| name.endsWith(".ape") ||name.endsWith(".flac")){
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_music);
            } else if (name.endsWith(".mp4")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_media);
            }else if (name.endsWith(".xls") || name.endsWith(".xlsx")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_excel);
            } else if (name.endsWith(".torrent")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_torrent);
            } else if (name.endsWith(".ppt")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_ppt);
            } else if (name.endsWith(".zip") || name.endsWith(".rar") || name.endsWith(".tar.gz")) {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_zip);
            } else if (name.endsWith(".bmp") || name.endsWith(".jpg") || name.endsWith("jpeg")||name.endsWith(".png")){
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_picture);
                String absolutePath = mFileList.get(position).getAbsolutePath();
                Glide.with(mContext).load(new File(absolutePath)).asBitmap().into(viewHolder.ivFileIcon);
            } else if (name.endsWith(".apk")) {
                String apkPath = mFileList.get(position).getAbsolutePath();
                PackageManager pm = mContext.getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(apkPath,PackageManager.GET_ACTIVITIES);
                if (info != null) {
                    ApplicationInfo appInfo = info.applicationInfo;
                    Drawable applicationIcon = pm.getApplicationIcon(appInfo);
                    viewHolder.ivFileIcon.setBackground(applicationIcon);
                }
            }
            else {
                viewHolder.ivFileIcon.setBackgroundResource(R.mipmap.format_unkown);
            }
        }
        viewHolder.tvFileName.setText(mFileList.get(position).getName());
        DateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.tvFileCreateTime.setText(formate.format(mFileList.get(position).lastModified()));
        return convertView;
    }

    static class FileListViewHolder {
        ImageView ivFileIcon ;
        TextView tvFileName;
        TextView tvFileCreateTime;
    }
}
