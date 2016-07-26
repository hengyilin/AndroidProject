package cn.hylin.edu.szu.mynewsapp.service;

import cn.hylin.edu.szu.mynewsapp.model.VideoSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author：林恒宜 on 16-7-17 09:13
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public interface SearchVideoService {
    @GET("onebox/movie/video")
    Call<VideoSearchResponse> getVideoSearchResponse(@Query("key")String apiKey,
                                                     @Query("q")String name);
}
