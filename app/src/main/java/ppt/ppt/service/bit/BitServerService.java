package ppt.ppt.service.bit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dj_hwang on 2017. 9. 21..
 */

public interface BitServerService {

    //headline 조회
    @GET("/ppt/crawler/recentNews.do")
    Call<Object> getHeadlineNews();

    //카테고리별 뉴스 조회
    @GET("/ppt/crawler/recentNews.do")
    Call<Object> getCategoryNews(@Query("newsCode") String newsCode, @Query("num") int num);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://222.106.22.63:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();
}
