package ppt.ppt.service.api;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dj_hwang on 2017. 9. 21..
 */

public interface APIServerService {

    @GET("/markets/api/bulk-time-series/price/{comCode}")
    Call<Object> getPrice(@Path("comCode") String comCode, @Query("time") String time);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.bloomberg.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();

}
