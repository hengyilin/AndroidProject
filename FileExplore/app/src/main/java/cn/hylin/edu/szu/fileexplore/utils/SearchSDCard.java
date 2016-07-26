package cn.hylin.edu.szu.fileexplore.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author：林恒宜 on 16-7-24 16:04
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class SearchSDCard {
    public static List<File> searchFileByDir(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        List<File> allFileList = new ArrayList<>();
        if (files.length > 0) {
            List<File> dirList = new ArrayList<>();
            List<File> fileList = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                if (!files[i].getName().startsWith(".")) {


                    if (files[i].isFile()) {
                        fileList.add(files[i]);
                    }
                    if (files[i].isDirectory()) {
                        dirList.add(files[i]);
                    }
                }
            }
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    return lhs.getName().compareToIgnoreCase(rhs.getName());
                }
            });
            Collections.sort(dirList, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    return lhs.getName().compareToIgnoreCase(rhs.getName());
                }
            });
            allFileList.addAll(dirList);
            allFileList.addAll(fileList);
        }
        return allFileList;
    }

}
