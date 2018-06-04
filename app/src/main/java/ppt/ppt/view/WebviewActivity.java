package ppt.ppt.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        //Toast.makeText(getApplicationContext(), intent.getStringExtra("link"), Toast.LENGTH_LONG).show();

        WebView webView = (WebView)findViewById(R.id.webview);
        webView.loadUrl(intent.getStringExtra("link"));
    }
}
