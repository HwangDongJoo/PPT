package ppt.ppt.service.bit;

import java.util.List;

import okhttp3.OkHttpClient;
import ppt.ppt.vo.CompanyVO;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dj_hwang on 2017. 9. 22..
 */

public interface UserServerService {

    //로그인
    @POST("/ppt/loginM.do")
    Call<Object> login(@Query("email") String email, @Query("password") String password);

    //아이디 중복확인
    @POST("/ppt/idCheck.json")
    Call<Object> checkId(@Query("email")String email);

    @GET("/ppt/company/selectCompanyList.json")
    Call<String> allCompanyList();

    //회원가입
    @POST("/ppt/joinM.do")
    Call<Object> join(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("tel") String tel);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://222.106.22.63:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();

}
