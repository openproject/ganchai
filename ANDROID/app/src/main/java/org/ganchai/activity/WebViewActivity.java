package org.ganchai.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;

public class WebViewActivity extends BaseActivity {
    public static final String KEY_URL = "url";

    private WebView webView;
    private String url;

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setTitle("正在加载...");
            webView.getSettings().setBlockNetworkImage(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.getSettings().setBlockNetworkImage(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initToolbar();

        url = getIntent().getStringExtra(KEY_URL);
        webView = ViewLess.$(this, R.id.webview);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        setWebSetting(webView);

        webView.loadUrl(url);
    }

    private void setWebSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
