package ppt.ppt.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import ppt.ppt.service.bit.UserServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private Button btnJoin, btnCheckId;
    private EditText editId, editPassword, editRePassword, editName, editPhone;
    private UserServerService userServerService;
    private int check = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        init();

        userServerService = UserServerService.retrofit.create(UserServerService.class);

        btnCheckId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editId.getText().toString();

                final Call<Object> checkId = userServerService.checkId(email);
                checkId.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Gson gson = new Gson();
                        String ab = gson.toJson(response.body());
                        check = (int)Double.parseDouble(ab);

                        //Log.e("ppt", String.valueOf(d));

                        if(check==1)
                            Toast.makeText(getApplicationContext(), "이미 존재하는 계정입니다. 다시 설정해주세요", Toast.LENGTH_LONG).show();
                        else if(check==0)
                            Toast.makeText(getApplicationContext(), "사용하실 수 있는 계정입니다. 감사합니다.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "접속실패", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editId.getText().toString();
                String password = editPassword.getText().toString();
                final String repassword = editRePassword.getText().toString();
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();

                if(!checkPassword(password, repassword)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                } else {

                    if(check == -1) {
                        Toast.makeText(getApplicationContext(), "계정 중복 확인 해주세요", Toast.LENGTH_LONG).show();
                    } else if(check == 1) {
                        Toast.makeText(getApplicationContext(), "이미 존재하는 계정입니다. 다시 설정해주세요", Toast.LENGTH_LONG).show();
                    } else if(check == 0) {
                        Call<Object> joinCall = userServerService.join(id, password, name, phone);
                        joinCall.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Gson gson = new Gson();
                                String check = gson.toJson(response.body());
                                int join = (int)Double.parseDouble(check);

                                if(join==1){
                                    Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다. 감사합니다.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if(join==0){
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 시도해 주세요", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.e("ppt", "회원가입 메소드 접속 실패");
                            }
                        });
                    }

                }
            }
        });

    }

    /**
     * 뷰를 초기화 하는 메소드
     */
    void init(){
        btnJoin = (Button)findViewById(R.id.join_btn_join);
        btnCheckId = (Button)findViewById(R.id.join_btn_checkId);
        editId = (EditText)findViewById(R.id.join_edit_id);
        editPassword = (EditText)findViewById(R.id.join_edit_password);
        editRePassword = (EditText)findViewById(R.id.join_edit_repassword);
        editName = (EditText)findViewById(R.id.join_edit_name);
        editPhone = (EditText)findViewById(R.id.join_edit_phone);
    }

    /**
     * 비밀번호와 비밀번호 확인 안에 텍스트들이 같은지 확인하는 메소드
     * @param s1
     * @param s2
     * @return
     */
    boolean checkPassword(String s1, String s2){
        return s1.equals(s2);
    }
}
