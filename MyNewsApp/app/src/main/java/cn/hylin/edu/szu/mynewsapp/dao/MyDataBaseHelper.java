package cn.hylin.edu.szu.mynewsapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.model.City;

/**
 * Author：林恒宜 on 16-7-17 14:06
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class MyDataBaseHelper {
    private final String ASSETS_NAME = "china_cities.db";
    private final String DB_NAME = "china_cities.db";
    private final String TABLE_NAME = "city";
    private final String NAME = "name";
    private final String PINYIN = "pinyin";
    private final int BUFFER_SIZE =1024;
    private String PATH ;
    private Context context;

    public MyDataBaseHelper(Context context) {
        this.context = context;
        PATH  = File.separator + "data" + Environment.getDataDirectory()
                .getAbsolutePath() + File.separator + context.getPackageName()
                + File.separator + "databases" + File.separator;
    }

    /**
     * 把数据库拷贝到SD卡中
     */
    public void copyFile() {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(PATH + DB_NAME);
        if (!dbFile.exists()) {
            InputStream is ;
            OutputStream os;
            try {
                is = context.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                int length;
                byte[] buffer = new byte[BUFFER_SIZE];
                while((length = is.read(buffer)) != -1) {
                    os.write(buffer,0,length);
                }
                os.flush();
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询所有的城市信息
     * @return
     */
    public List<City> selectAllCity() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);
        List<City> citys = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));

            City city = new City(name,pinyin);
            citys.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(citys,new CityCompartor());//利用集合工具类对集合进行排序
        return citys;
    }

    /**
     * 根据拼音查询城市信息
     *
     * @return
     */
    public List<City> selectCityByPinYin(final String selection) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(PATH + DB_NAME, null);

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +" where name like \"%" + selection
                + "%\" or pinyin like \"%" + selection + "%\"" ,null);
        List<City> cities = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));

            City city = new City(name,pinyin);
            cities.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(cities,new CityCompartor());
        return cities;
    }

    /**
     * 一个可以比较的类；让城市名称根据第一个拼音字母的大小比较
     */
    public class CityCompartor implements Comparator<City> {

        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPingyin().substring(0,1);
            String b = rhs.getPingyin().substring(0,1);
            return a.compareTo(b);
        }
    }
}
