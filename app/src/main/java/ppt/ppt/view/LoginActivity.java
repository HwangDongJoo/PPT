package ppt.ppt.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ppt.ppt.service.bit.UserServerService;
import ppt.ppt.vo.UserVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnJoin, btnLogin;
    private EditText editId, editPassword;
    private CheckBox checkSaveId, checkAutoLogin;
    private UserServerService userServerService;
    private UserVO userVO = new UserVO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        editId.setText(setId());

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editId.getText().toString();
                final String password = editPassword.getText().toString();

                userServerService = UserServerService.retrofit.create(UserServerService.class);


                Call<Object> checkLogin = userServerService.login(email, password);
                checkLogin.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Gson gson = new Gson();
                        String s = gson.toJson(response.body());
                        Log.e("ppt", s);

                        String result = null;
                        String id = null;
                        String domain = null;
                        String name  = null;
                        String phone = null;
                        String email = null;
                        int login=0;

                        try {
                            JSONObject json = new JSONObject(s);
                            result = json.getString("result");
                            id = json.getString("id");
                            domain = json.getString("domain");
                            name = json.getString("name");
                            phone = json.getString("tel");

                            /*Log.e("ppt", result);
                            Log.e("ppt", id);
                            Log.e("ppt", domain);
                            Log.e("ppt", name);
                            Log.e("ppt", phone);*/

                            userVO.setId(id);
                            userVO.setDomain(domain);
                            userVO.setName(name);
                            userVO.setTel(phone);

                            login = Integer.parseInt(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(login==0){
                            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀렸습니다. 다시 확인해주세요", Toast.LENGTH_LONG).show();
                        } else if(login==1){
                            StringBuffer sb = new StringBuffer();
                            sb.append(id).append("@").append(domain);
                            email = sb.toString();
                            if(checkSaveId.isChecked()) saveId(email);
                            else removeId();

                            if(checkAutoLogin.isChecked()) saveUserInfo(userVO);
                            else removeUserInfo();

                            Toast.makeText(getApplicationContext(), name + "님 로그인에 성공했습니다.", Toast.LENGTH_LONG).show();
                            Intent it = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(it);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e("ppt", "로그인 메소드 접속실패");
                    }
                });


            }
        });
    }

    void init(){
        btnJoin = (Button)findViewById(R.id.login_btn_join);
        btnLogin = (Button)findViewById(R.id.login_btn_login);
        editId = (EditText)findViewById(R.id.login_edit_id);
        editPassword = (EditText)findViewById(R.id.login_edit_password);
        checkSaveId = (CheckBox)findViewById(R.id.login_check_saveId);
        checkAutoLogin = (CheckBox)findViewById(R.id.login_check_autoLogin);
    }

    void saveId(String id){
        SharedPreferences sf = getSharedPreferences("UserId", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString("id", id);
        editor.commit();
    }

    void removeId(){
        SharedPreferences sf = getSharedPreferences("UserId", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.remove("id");
        editor.commit();
    }

    void saveUserInfo(UserVO userVO){
        SharedPreferences sf = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        StringBuffer sb = new StringBuffer();
        sb.append(userVO.getId()).append("@").append(userVO.getDomain());
        String id = sb.toString();
        editor.putString("id", id);
        editor.putString("name", userVO.getName());
        editor.putString("tel", userVO.getTel());
        editor.putString("check", "true");
        editor.commit();
    }

    void removeUserInfo(){
        SharedPreferences sf = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.remove("id");
        editor.remove("name");
        editor.remove("tel");
        editor.putString("check", "false");
        editor.commit();
    }

    String setId(){
        SharedPreferences sf = getSharedPreferences("UserId", MODE_PRIVATE);
        return sf.getString("id", "");
    }


}
