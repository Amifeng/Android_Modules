package com.bugzhu.thirdpay.paymodule;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bugzhu.thirdpay.R;

/**
 * Created by Bugzhu on 2017/11/9.
 */

public class VISAHtmlActivity extends Activity {

    private WebView webview;

    //private String order_sn = "";
    private String visa_html = "";

//    private String ht = "<html><head><metahttp-equiv="Content-Type"content="text/html; charset=utf-8"/></head><bodyonload="javascript:document.pay_form.submit();"><formid="pay_form"name="pay_form"action="https://gateway.test.95516.com/gateway/api/frontTransReq.do"method="post"><inputtype="hidden"name="version"id="version"value="5.1.0"/><inputtype="hidden"name="encoding"id="encoding"value="utf-8"/><inputtype="hidden"name="txnType"id="txnType"value="01"/><inputtype="hidden"name="txnSubType"id="txnSubType"value="01"/><inputtype="hidden"name="bizType"id="bizType"value="000201"/><inputtype="hidden"name="frontUrl"id="frontUrl"value="http://www.test.dev/pay/unionpay/mobile/demo/api_02_b2b/FrontReceive.php"/><inputtype="hidden"name="backUrl"id="backUrl"value="http://222.222.222.222:8080/upacp_demo_b2b/demo/api_02_b2b/BackReceive.php"/><inputtype="hidden"name="signMethod"id="signMethod"value="01"/><inputtype="hidden"name="channelType"id="channelType"value="08"/><inputtype="hidden"name="accessType"id="accessType"value="0"/><inputtype="hidden"name="currencyCode"id="currencyCode"value="156"/><inputtype="hidden"name="merId"id="merId"value="777290058110048"/><inputtype="hidden"name="orderId"id="orderId"value="20171225141734"/><inputtype="hidden"name="txnTime"id="txnTime"value="20171225141734"/><inputtype="hidden"name="txnAmt"id="txnAmt"value="1000"/><inputtype="hidden"name="payTimeout"id="payTimeout"value="20171225143942"/><inputtype="hidden"name="certId"id="certId"value="68759663125"/><inputtype="hidden"name="signature"id="signature"value="1ElgJqy48Itt+PzZOMT/BY+zd9P2jep6TqG8TSZVnuo17HAwuFHjU+tY/cOuJrtzJgkIt3K9UBpP94BhWJO/06wEjj0r0OBViTpn7PsMmp0thXIAPSqC8M1yu8CiyPRQRo7coWn9scb2iRWxOD5Gq6Vy6NUW/Xcmb2HZZIt70snwe2kJSf69pC6qDjYFdCBtBqf/jf62vXYkLFSU+hT+VsEBg2++rIoN5amc0Qk10uKwaAFz0eOTVRwCT6hCCHgtVeizIKgoif3JI9fUbLIGqu+mc79ABHrngCXh9hOWRZNWAJQB6LD5ohoibsiHf0yKzrsWgdGaTiOvNy8wUvUOYw=="/><!--<inputtype="submit"type="hidden">--></form></body></html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_html);
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, getString(R.string.operation_error), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else
            mInit();
    }

    private void mInit() {
        //order_sn = getIntent().getStringExtra("order_sn");
        visa_html = getIntent().getStringExtra("visa_html");

        webview = (WebView) findViewById(R.id.webview);

        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings webSettings = webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");//设置默认为utf-8
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);

        //设置参数
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheEnabled(true);// 设置缓存

        webview.setWebChromeClient(new WebChromeClient());

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                ToastUtils.showShortToast(VISAHtmlActivity.this, url);
                //https://cashier.test.95516.com/b2c/payResult.action?transNumber=825456135280829738801
                if (url.indexOf("callback.action?") > -1 && url.indexOf("transNumber=") > -1) {
                    setResult(PayCode.RESULT_CODE_PAYMENT_SUCCEED);
                    finish();
                    return true;
                }

                //https://cashier.test.95516.com/b2c/api/Pay.action
                if (url.indexOf("Pay.action") > -1) {
                    setResult(PayCode.RESULT_CODE_PAYMENT_ERROR);
                    finish();
                    return true;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//              Toast.makeText(VISAHtmlActivity.this, getString(R.string.operation_loading), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
//
        webview.loadDataWithBaseURL(null, visa_html, "text/html", "utf-8", null);

    }

    private void mListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webview != null) {
            webview.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webview != null) {
            webview.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (webview != null) {
            webview.destroy();
            webview = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setResult(PayCode.RESULT_CODE_PAYMENT_CANCEL);
            finish();
//            if (webview.canGoBack()) {
//                webview.goBack(); // goBack()表示返回WebView的上一页面
//            } else {
//                setResult(PayCode.RESULT_CODE_PAYMENT_CANCEL);
//                finish();
//            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
