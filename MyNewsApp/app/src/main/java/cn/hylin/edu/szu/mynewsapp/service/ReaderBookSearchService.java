package cn.hylin.edu.szu.mynewsapp.service;

import java.util.List;

import cn.hylin.edu.szu.mynewsapp.model.BookListInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author：林恒宜 on 16-7-25 18:37
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public interface ReaderBookSearchService {
    @GET("92-92/")
    Call<List<BookListInfo>> getBookListById(@Query("id") String id,
                                             @Query("limit")String limit,
                                             @Query("showapi_appid")String showapi_appid,
                                             @Query("showapi_sign")String showapi_sign);
}
