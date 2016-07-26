package cn.hylin.edu.szu.mynewsapp.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Author：林恒宜 on 16-7-17 16:01
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class PinYiUtils  {
    public static String getFirstLetter(final String pinyin) {
        if (TextUtils.isEmpty(pinyin)) {
            return "定位";
        }
        String firstLetter = pinyin.substring(0, 1);
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");//正则表达式格式：^开始$结束+代表可以出现零次或者多次[]表示一个范围
        if (pattern.matcher(firstLetter).matches()) {
            return firstLetter.toUpperCase();
        }else if ("0".equals(firstLetter)) {
            return "定位";
        } else if ("1".equals(firstLetter)) {
            return "热门";
        }
        return "定位";
    }
}
