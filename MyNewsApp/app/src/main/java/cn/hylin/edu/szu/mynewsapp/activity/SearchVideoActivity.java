package cn.hylin.edu.szu.mynewsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.VideoSearchResponse;
import cn.hylin.edu.szu.mynewsapp.service.SearchVideoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchVideoActivity extends AppCompatActivity {

    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.tvActor)
    TextView tvActor;
    @BindView(R.id.tvTag)
    TextView tvTag;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.viewgroup)
    ViewGroup viewGroup;
    @BindView(R.id.etInputSearch)
    EditText etInputSearch;
    @BindView(R.id.tvCancel)
    TextView tvCancel;

    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        ButterKnife.bind(this);
        etInputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchText = etInputSearch.getText().toString();
                    getDataFromServer(searchText);
                }
                return false;
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etInputSearch.setText("");
            }
        });

    }

    public void getDataFromServer(String searchText) {
        String search = "";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.VIDEO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        SearchVideoService videoService = retrofit.create(SearchVideoService.class);
        try {
            search = URLEncoder.encode(searchText,"utf-8");
            Log.i(Constants.DEBUG_TAG,"search = " + search);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Call<VideoSearchResponse> videoInfo = videoService.getVideoSearchResponse(search, Constants.VIDEO_API_KEY);
        videoInfo.enqueue(new Callback<VideoSearchResponse>() {
            @Override
            public void onResponse(Call<VideoSearchResponse> call, Response<VideoSearchResponse> response) {
                if (response.isSuccessful()) {
                    VideoSearchResponse body = response.body();
                    Log.i(Constants.DEBUG_TAG,"返回的结果是：" + body.getReason());
                    Log.i(Constants.DEBUG_TAG,"返回的结果是：" + body.getError_code());
                    //Log.i(Constants.DEBUG_TAG,"返回的结果是：" + body.getResult().getAct());
                }
            }
            @Override
            public void onFailure(Call<VideoSearchResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"查找失败，请确定查找条件",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
