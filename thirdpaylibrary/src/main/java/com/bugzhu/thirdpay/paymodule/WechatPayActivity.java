package com.bugzhu.thirdpay.paymodule;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.bugzhu.thirdpay.R;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WechatPayActivity extends Activity {

    private Wechat model;
    private IWXAPI msgApi ;
    private payStatus pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_module_dialog);

        //广播
        pay=new payStatus();
        IntentFilter filter=new IntentFilter("we.chat.pay");
        registerReceiver(pay, filter);

        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, getString(R.string.operation_error), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            model = (Wechat) intent.getSerializableExtra("wechat");
            doPayment();
        }
    }

    private void doPayment() {
        msgApi = WXAPIFactory.createWXAPI(WechatPayActivity.this, model.getAppid(),true);

        if (msgApi.isWXAppInstalled() && msgApi.isWXAppSupportAPI()) {
            msgApi.registerApp(model.getAppid());
            PayReq request = new PayReq();
            request.appId = model.getAppid();
            request.partnerId = model.getPartnerid();
            request.prepayId = model.getPrepayid();
            request.packageValue = model.getPackages();
            request.nonceStr = model.getNoncestr();
            request.timeStamp = model.getTimestamp();
            request.sign = model.getSign();
            msgApi.sendReq(request);
        } else {
            Toast.makeText(this, getString(R.string.wxappinstalled), Toast.LENGTH_SHORT).show();
            setResult(PayCode.RESULT_CODE_PAYMENT_ERROR);
            finish();
        }
    }

    class payStatus extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode=intent.getIntExtra("errCode", 0);
//            Toast.makeText(context, "errCode = " +errCode , Toast.LENGTH_SHORT).show();
            switch(errCode){
                case PayCode.ERR_OK://支付成功
                    ((Activity) context).setResult(PayCode.RESULT_CODE_PAYMENT_SUCCEED);
                    break;
                case PayCode.ERR_COMM://支付失败
                    ((Activity) context).setResult(PayCode.RESULT_CODE_PAYMENT_ERROR);
                    break;
                case PayCode.ERR_USER_CANCEL://取消支付
                    ((Activity) context).setResult(PayCode.RESULT_CODE_PAYMENT_CANCEL);
                    break;
                default:
                    ((Activity) context).setResult(PayCode.RESULT_CODE_PAYMENT_ERROR);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(pay);
    }
}
