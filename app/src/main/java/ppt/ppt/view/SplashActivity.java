package ppt.ppt.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent it = null;
                if(checkPreferences()) {
                    Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    it = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    boolean checkPreferences(){
        SharedPreferences sf = getSharedPreferences("UserInfo", MODE_PRIVATE);

        String check =  sf.getString("check", "");

        if(check.equals("true")) return true;
        else return false;
    }
}
