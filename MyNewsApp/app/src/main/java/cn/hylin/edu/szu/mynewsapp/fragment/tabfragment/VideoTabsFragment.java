package cn.hylin.edu.szu.mynewsapp.fragment.tabfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.VideoRecentInfoResponse;
import cn.hylin.edu.szu.mynewsapp.service.VideoRecentInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：林恒宜 on 16-7-18 23:19
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class VideoTabsFragment extends Fragment {

    private View view;
    private String text;
    private Object recentVideoInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            text = arguments.getString("text");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_video_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(text);
        //getRecentVideoInfo("深圳");
        return view;
    }

    public void getRecentVideoInfo(String mCity) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.VIDEO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        VideoRecentInfoService service = retrofit.create(VideoRecentInfoService.class);
        String city = null;
        try {
            city = URLEncoder.encode(mCity,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (city == null) {
            Toast.makeText(getActivity(),"参数为空",Toast.LENGTH_SHORT).show();
            return ;
        }
        Call<VideoRecentInfoResponse> call = service.getVideoRecentInfoResponse(Constants.VIDEO_API_KEY,city);
        call.enqueue(new Callback<VideoRecentInfoResponse>() {
            @Override
            public void onResponse(Call<VideoRecentInfoResponse> call, Response<VideoRecentInfoResponse> response) {
                if (response.isSuccessful()) {
                    VideoRecentInfoResponse body = response.body();
                    Log.i(Constants.DEBUG_TAG,"结果码是：" + body.getError_code() + "返回的结果是" + body.getResult());
                }

            }

            @Override
            public void onFailure(Call<VideoRecentInfoResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"访问失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
