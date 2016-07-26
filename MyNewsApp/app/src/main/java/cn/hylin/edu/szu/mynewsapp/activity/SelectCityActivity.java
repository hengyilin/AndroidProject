package cn.hylin.edu.szu.mynewsapp.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.adapter.AllCityListViewAdapter;
import cn.hylin.edu.szu.mynewsapp.adapter.ResultCityAdapter;
import cn.hylin.edu.szu.mynewsapp.dao.MyDataBaseHelper;
import cn.hylin.edu.szu.mynewsapp.model.City;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.service.LocateAddressService;
import cn.hylin.edu.szu.mynewsapp.view.SideLetterBar;

public class SelectCityActivity extends AppCompatActivity {

    @BindView(R.id.lvAllCity)
    ListView lvAllCity;
    @BindView(R.id.lvResultCity)
    ListView lvResult;
    @BindView(R.id.slideLetterbar)
    SideLetterBar sideLetterBar;
    @BindView(R.id.etSeacher)
    EditText etSearcher;
    @BindView(R.id.ibClear)
    ImageButton ibClear;
    @BindView(R.id.tvOverLay)
    TextView tvOverLay;
    @BindView(R.id.tvFailTips)
    TextView tvFailTips;

    private List<City> cities;
    private AllCityListViewAdapter mAllCitiesAdapter;
    private MyDataBaseHelper myDb;
    private ResultCityAdapter resultCityAdapter;

    private LocateAddressService.IMyBinder iMyBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("选择城市");
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        initData();
        initView();
//        Intent serviceIntent = new Intent(this, LocateAddressService.class);
//        MyServiceConn myServiceConn = new MyServiceConn();
//        bindService(serviceIntent,myServiceConn,BIND_AUTO_CREATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                openGPSLocation();
            }
        }).start();

    }

    private void initData() {
        myDb = new MyDataBaseHelper(this);
        myDb.copyFile();    // 把数据从应用中拷贝到SD卡中
        cities = myDb.selectAllCity();//查询所有城市的信息
        mAllCitiesAdapter = new AllCityListViewAdapter(cities, this);//为Adapter设置适配器

        resultCityAdapter = new ResultCityAdapter(this, null);
    }

    private void initView() {

        lvAllCity.setAdapter(mAllCitiesAdapter);//所有城市的ListView设置适配器
        lvResult.setAdapter(resultCityAdapter); //
        sideLetterBar.setTvOverlay(tvOverLay);

        lvAllCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void OnLetterChange(String letter) {
                int letterPostion = mAllCitiesAdapter.getLetterPostion(letter);
                letterPostion = letterPostion + 2;
                lvAllCity.setSelection(letterPostion);
            }
        });

        /**
         * 输入框设置监听器用addTextChangedListener
         */
        etSearcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etSearcher.getText().toString())) {
                    ibClear.setVisibility(View.GONE);       //隐藏清空小图标
                    lvResult.setVisibility(View.GONE);      //隐藏匹配结果页面
                    tvFailTips.setVisibility(View.GONE);    //隐藏没有匹配结果的页面
                } else {
                    ibClear.setVisibility(View.VISIBLE);
                    lvResult.setVisibility(View.VISIBLE);
                    List<City> cities = myDb.selectCityByPinYin(etSearcher.getText().toString());
                    if (cities == null || cities.size() == 0) {
                        lvResult.setVisibility(View.GONE);
                        tvFailTips.setVisibility(View.VISIBLE);
                    } else {
                        tvFailTips.setVisibility(View.GONE);
                        resultCityAdapter.dataChange(cities);
                    }
                }
            }
        });
        ibClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearcher.setText("");
            }
        });
    }

    /**
     * 服务连接类
     */
    public class MyServiceConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyBinder = (LocateAddressService.IMyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    /**
     * 开启定位功能
     */
    private void openGPSLocation(){
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //如果GPS模块可以使用那么就进行GPS定位
            locateAddress();
        } else {
            Toast.makeText(this,"GPS没有打开，请自行前往设置",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 定位的具体操作
     */
    private void locateAddress() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
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

        Log.i(Constants.DEBUG_TAG,"经度:" + latitude + "维度：" + longtitude);

//        getCityByCoordinate(latitude,longtitude);
        setTapCoordinates(latitude,longtitude);
    }

    public void setTapCoordinates(double latitude,double longtitude) {

        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(latitude / 1E6, longtitude / 1E6, 1);
            StringBuilder add = new StringBuilder();
            if (addresses.size() > 0) {
                int max = addresses.get(0).getMaxAddressLineIndex();
                add.append(addresses.get(0).getAddressLine(max - 1));
                //取得全部名称时如下
//                for (int i = 0; i < max; i++) {
//                    add.append(addresses.get(0).getAddressLine(i) + " ");
//                }
            }
            Log.i(Constants.DEBUG_TAG,"城市名城是 " + add.toString());
//            Toast.makeText(this, add.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.i(Constants.DEBUG_TAG,"UNKNOWN");
//            Toast.makeText(this, "UNKNOWN", Toast.LENGTH_SHORT).show();
        }
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
                    String result = os.toString();
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
}
