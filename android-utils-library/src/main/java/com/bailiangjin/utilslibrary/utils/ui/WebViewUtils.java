package com.bailiangjin.utilslibrary.utils.ui;

import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bailiangjin.utilslibrary.interfaze.listener.MyWebViewDownLoadListener;

/**
 * Created by bailiangjin on 15/8/19.
 */
public class WebViewUtils {

    /**
     * 根据url加载Web界面
     * @param webView
     * @param url
     */
   public static void load(WebView webView,String url ){


//       // 开启 localStorage
//       WebSettings webSettings=webView.getSettings();
//      LogUtils.d("OverScrollMode"+webView.getOverScrollMode());
//
//       if (Build.VERSION.SDK_INT >= 11){
//           webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//       }
//
////       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////           // chromium, enable hardware acceleration
////           webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
////       } else {
////           // older android version, disable hardware acceleration
////           webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
////       }
////       webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//
//       webSettings.setSupportZoom(true);
//       webSettings.setBuiltInZoomControls(true);
//       webSettings.setDomStorageEnabled(true);
//       webSettings.setBlockNetworkImage(false);
//       // 设置支持javascript
//       webSettings.setJavaScriptEnabled(true);
////       webSettings.setEnableSmoothTransition(true);
//       // 启动缓存
////       webSettings.setAppCacheEnabled(false);
//       webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//       // 设置缓存模式
////       webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


       // 开启 localStorage
       webView.getSettings().setDomStorageEnabled(true);
       // 设置支持javascript
       webView.getSettings().setJavaScriptEnabled(true);
       // 启动缓存
       webView.getSettings().setAppCacheEnabled(true);
       // 设置缓存模式
       webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
       //使用自定义的WebViewClient
       webView.setWebViewClient(new WebViewClient() {
           //覆盖shouldOverrideUrlLoading 方法
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               view.loadUrl(url);
               return true;
           }
       });

       webView.setWebChromeClient(new WebChromeClient());
       //文件下载监听
       webView.setDownloadListener(new MyWebViewDownLoadListener());

       webView.loadUrl(url);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           webView.onResume();
       }
   }
}
