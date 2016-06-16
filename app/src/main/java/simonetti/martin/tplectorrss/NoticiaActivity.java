package simonetti.martin.tplectorrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NoticiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        android.support.v7.app.ActionBar ab= getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        String url= getIntent().getExtras().getString("url");
        WebView webView= (WebView) findViewById(R.id.wvNoticia);
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if (id== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
