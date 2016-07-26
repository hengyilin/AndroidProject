package cn.hylin.edu.szu.mynewsapp.service;

import cn.hylin.edu.szu.mynewsapp.model.VideoRecentInfoResponse;
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
public interface VideoRecentInfoService  {
    @GET("onebox/movie/pmovie")
    Call<VideoRecentInfoResponse> getVideoRecentInfoResponse (@Query("key")String apikey,
                                                              @Query("city")String city);
}
