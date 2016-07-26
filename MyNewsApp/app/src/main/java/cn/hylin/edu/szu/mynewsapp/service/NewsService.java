package cn.hylin.edu.szu.mynewsapp.service;

import cn.hylin.edu.szu.mynewsapp.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author：林恒宜 on 16-7-15 13:06
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public interface NewsService {
    @GET("toutiao/index")
    Call<NewsResponse> getResponse(@Query("key") String key, @Query("type") String type);
}
