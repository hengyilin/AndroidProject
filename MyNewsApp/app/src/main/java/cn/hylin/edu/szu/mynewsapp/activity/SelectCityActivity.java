package cn.hylin.edu.szu.mynewsapp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.adapter.AllCityListViewAdapter;
import cn.hylin.edu.szu.mynewsapp.adapter.ResultCityAdapter;
import cn.hylin.edu.szu.mynewsapp.dao.MyDataBaseHelper;
import cn.hylin.edu.szu.mynewsapp.model.City;
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
        Intent serviceIntent = new Intent(this, LocateAddressService.class);
        MyServiceConn myServiceConn = new MyServiceConn();
        bindService(serviceIntent,myServiceConn,BIND_AUTO_CREATE);

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

    public class MyServiceConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyBinder = (LocateAddressService.IMyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
