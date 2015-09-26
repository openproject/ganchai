package org.ganchai.extend.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.extend.BaseExtendActivity;

import xyz.ganchai.activity.SearchActivity;

public class ExtendWeixinWebviewActivity extends BaseActivity {

    public static final String KEY_URL = "url";

    private WebView webView;
    private String url;

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public WebResourceResponse shouldInterceptRequest(final WebView view, final String url) {
            if (url.startsWith("http://mp.weixin.qq.com/s?__biz")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(ExtendWeixinWebviewActivity.this, url, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ExtendWeixinWebviewActivity.this, WebViewActivity.class);
            intent.putExtra(BaseExtendActivity.KEY_TITLE, view.getTitle());
            intent.putExtra(WebViewActivity.KEY_URL, url);
                        startActivity(intent);
                    }
                });

            }

            return super.shouldInterceptRequest(view, url);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_weixin_webview);

        url = getIntent().getStringExtra(KEY_URL);

        initToolbar();

        webView = ViewLess.$(this, R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(webViewClient);

        webView.loadUrl(url);
    }

}
