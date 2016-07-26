package cn.hylin.edu.szu.mynewsapp.utils;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hylin.edu.szu.mynewsapp.model.Constants;
import cn.hylin.edu.szu.mynewsapp.model.NewsResponse;
import cn.hylin.edu.szu.mynewsapp.service.NewsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：林恒宜 on 16-7-15 23:49
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description : 该异步任务用于访问服务器并更新页面
 */
public class MyAsyncTask extends AsyncTask<String ,Integer, NewsResponse.NewsResponseBody[]> {

//    private ProgressBar mProgressBar;
//    private ListView mListView;
//    private Context context;
//    private String mType;
//    private boolean isRefresh;

    private NewsResponse.NewsResponseBody[] data;
    private BaseAdapter mListViewAdapter;
    private List<NewsResponse.NewsResponseBody> result = new ArrayList<>();

//    public MyAsyncTask(ProgressBar mProgressBar, ListView mListView, Context context,
//                       String mType, boolean isRefresh) {
//        this.mProgressBar = mProgressBar;
//        this.mListView = mListView;
//        this.context = context;
//        this.mType = mType;
//        this.isRefresh = isRefresh;
//    }

    public MyAsyncTask() {
        super();
    }

    /**
     * 在开始执行任务之前的准备工作
     */
    @Override
    protected void onPreExecute() {
        //mProgressBar.setVisibility(View.VISIBLE);//显示进度条
        super.onPreExecute();
    }

    /**
     * 这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务。任务一旦完成就可以通过return语句
     * 来将任务的执行结果进行返回，如果AsyncTask的第三个泛型参数指定的是Void，就可以不返回任务执行结果。注意，在这
     * 个方法中是不可以进行UI操作的，如果需要更新UI元素，比如说反馈当前任务的执行进度，可以调用
     * publishProgress(Progress...)方法来完成。
     * @param params 传递进来的参数
     * @return 返回执行的结果
     */
    @Override
    protected NewsResponse.NewsResponseBody[] doInBackground(String... params) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        NewsService newsService = retrofit.create(NewsService.class);
        Call<NewsResponse> call = newsService.getResponse(Constants.API_KEY, params[0]);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    NewsResponse.NewsResult result = newsResponse.getResult();
                    data = result.getData();
                    for (int i = 0; i < data.length; i ++) {
                        System.out.println("新闻标题：" + data[i].getTitle());
                    }
                }
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
               // Toast.makeText(context,"获取数据失败，请检查网络设置",Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

    /**
     * 当在后台任务中调用了publishProgress(Progress...)方法后，这个方法就很快会被调用，方法中携带的参数就是在
     * 后台任务中传递过来的。在这个方法中可以对UI进行操作，利用参数中的数值就可以对界面元素进行相应的更新。
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     * 当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用。返回的数据会作为参数传递到此方法中，
     * 可以利用返回的数据来进行一些UI操作，比如说提醒任务执行的结果，以及关闭掉进度条对话框等
     * @param newsResponseBody
     */
    @Override
    protected void onPostExecute(NewsResponse.NewsResponseBody[] newsResponseBody) {
        //mProgressBar.setVisibility(View.INVISIBLE);//隐藏进度条
//        if (isRefresh) {
//            //刷新列表重新请求服务器
//            result.clear();
//            for (int i = 0; i < data.length; i ++) {
//                result.add(0,data[i]);
//            }
//        }else {
//            //加载更多
//            for (int i = 0; i < data.length; i ++) {
//                result.add(data[i]);
//            }
//        }
//        for (NewsResponse.NewsResponseBody body: result) {
//            Log.i(Constants.DEBUG_TAG,"标题为：" + body.getTitle());
//        }
//        if (mListViewAdapter == null ) {
//            mListViewAdapter = new NewsListViewAdapter(result,context);
//            mListView.setAdapter(mListViewAdapter);
//        }else {
//            mListViewAdapter.notifyDataSetChanged();
//        }
        super.onPostExecute(newsResponseBody);
    }
}
