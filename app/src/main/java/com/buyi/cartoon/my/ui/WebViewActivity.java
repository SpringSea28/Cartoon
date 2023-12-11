package com.buyi.cartoon.my.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.buyi.cartoon.databinding.ActivityWebviewBinding;
import com.buyi.cartoon.main.base.BaseActivity;


public class WebViewActivity extends BaseActivity<ActivityWebviewBinding> {

    private static final String TAG = ActivityWebviewBinding.class.getSimpleName();
    private static final String TITLE = "title";
    private static final String URL = "url";

    @NonNull
    @Override
    protected ActivityWebviewBinding getBindingView() {
        return ActivityWebviewBinding.inflate(getLayoutInflater());
    }

    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected String getTAG() {
        return TAG;
    }


    @Override
    protected void initAllMembersData(Bundle savedInstanceState) {
        setPadding(true);
        String title = getIntent().getStringExtra(TITLE);
        String url = getIntent().getStringExtra(URL);

        binding.title.tvTile.setText(title);
        initWebView();
        binding.webview.loadUrl(url);
        binding.title.imgBack.setOnClickListener(v -> onBackPress());
    }

    private void initWebView() {
        WebSettings settings = binding.webview.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //设置缓存
        settings.setJavaScriptEnabled(true);//设置能够解析Javascript
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        settings.setSupportMultipleWindows(false);
        binding.webview.setOnLongClickListener(v -> true);
        binding.webview.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void onDestroy() {
        if (binding.webview != null) {
            binding.webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            binding.webview.clearHistory();
            ((ViewGroup) binding.webview.getParent()).removeView(binding.webview);
            binding.webview.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(binding.webview.canGoBack()){
            binding.webview.goBack();
        }else {
            super.onBackPressed();
        }
    }

    private void onBackPress(){
        if(binding.webview.canGoBack()){
            binding.webview.goBack();
        }else {
            finish();
        }
    }
}
