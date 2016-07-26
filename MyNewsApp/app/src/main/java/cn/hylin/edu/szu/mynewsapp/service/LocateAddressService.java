package cn.hylin.edu.szu.mynewsapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.model.Constants;

public class LocateAddressService extends Service {

    private Context mContext;
    private String result = "";

    public LocateAddressService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        openGPSLocation();//开启定位功能
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();//返回一个对象的引用
    }

    /**
     * 开启定位功能
     */
    private void openGPSLocation(){
        LocationManager lm = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);//获得位置服务
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //如果GPS模块可以使用那么就进行GPS定位
            locateAddress();
        } else {
            Toast.makeText(mContext,"GPS没有打开，请自行前往设置",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 定位的具体操作
     */
    private void locateAddress() {
        LocationManager lm = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
//        criteria.setAltitudeRequired(false);
//        criteria.setBearingRequired(false);
//        criteria.setCostAllowed(false);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
        Location bestLocation = null;
        List<String>providerList = lm.getAllProviders();
        for (String currentProvider : providerList) {
            Location l = lm.getLastKnownLocation(currentProvider);
            if (l == null){
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }

        double latitude = bestLocation.getLatitude();
        double longtitude = bestLocation.getLongitude();

        getCityByCoordinate(latitude,longtitude);
    }

    /**
     * 根据经纬度返回所在城市的名称
     * @param latitude 经度
     * @param longtitude 维度
     * apikey xmKN9L2VMc2g9V0Tria1hKlCHNVX6Dgu
     * url http://api.map.baidu.com/geocoder/v2/
     */
    private void getCityByCoordinate(final double latitude, final double longtitude) {
        final String baseURL = "http://api.map.baidu.com/geocoder/v2/";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setRequestProperty("coordtype","wgs84ll");
                    connection.setRequestProperty("location",latitude + "," + longtitude);
                    connection.setRequestProperty("output","json");
                    connection.setRequestProperty("ak","xmKN9L2VMc2g9V0Tria1hKlCHNVX6Dgu");

                    InputStream is = connection.getInputStream();
                    OutputStream os = new ByteArrayOutputStream();
                    int length = -1;
                    byte[] buffer = new byte[1024];
                    while((length = is.read(buffer))!= -1){
                        os.write(buffer,0,length);
                    }
                    result = os.toString();
                    os.flush();
                    os.close();
                    is.close();
                    Log.i(Constants.DEBUG_TAG,result);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class MyBinder extends Binder implements IMyBinder{

        @Override
        public void invokeServiceMethod() {

        }
    }

    public interface IMyBinder {
        void invokeServiceMethod ();
    }
}
