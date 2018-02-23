package com.bugzhu.thirdpay.paymodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bugzhu.thirdpay.R;

import java.util.Map;

/**
 * Created by Pashley on 2016/9/17.
 */
public class AlipayClientActivity extends Activity {

    private static final int RQF_PAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_module_dialog);
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, getString(R.string.operation_error), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            String orderParam = intent.getStringExtra("alipay");
            doPay(orderParam);
        }
    }

    private void doPay(String orderParam) {
        final String payInfo = orderParam;

//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//沙箱测试

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(AlipayClientActivity.this);

                Map<String, String> result = alipay.payV2(payInfo, true);
                Message msg = new Message();
                msg.what = RQF_PAY;
                msg.obj = result.get("resultStatus");
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = msg.obj.toString();
            if (!isStrNull(result)) {
                setResult(PayCode.RESULT_CODE_ORDER);
                if (result.equals("9000")) {// 操作成功
                    setResult(PayCode.RESULT_CODE_PAYMENT_SUCCEED);
                } else if (result.equals("4000")) {// 系统异常
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_system_exception), Toast.LENGTH_SHORT).show();
                } else if (result.equals("4001")) {// 数据格式不正确
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_system_exception), Toast.LENGTH_SHORT).show();
                } else if (result.equals("4003")) {// 该用户绑定的支付宝账户被冻结或不允许支付
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_account_exception), Toast.LENGTH_SHORT).show();
                } else if (result.equals("4004")) {// 该用户已解除绑定
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_has_unbound), Toast.LENGTH_SHORT).show();
                } else if (result.equals("4005")) {// 绑定失败或没有绑定
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_system_exception), Toast.LENGTH_SHORT).show();
                } else if (result.equals("4006")) {// 订单支付失败
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_order_error), Toast.LENGTH_SHORT).show();
                } else if (result.equals("4010")) {// 重新绑定账户
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_system_exception), Toast.LENGTH_SHORT).show();
                } else if (result.equals("6000")) {// 支付服务正在进行升级操作
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_system_exception), Toast.LENGTH_SHORT).show();
                } else if (result.equals("6001")) {// 用户中途取消支付操作
                    setResult(PayCode.RESULT_CODE_PAYMENT_CANCEL);
                } else if (result.equals("7001")) {// 网页支付失败
                    setResult(PayCode.RESULT_CODE_PAYMENT_ERROR);
                } else if (result.equals("8000")) {// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    setResult(PayCode.RESULT_CODE_PAYMENT_CANCEL);
                } else {
                    Toast.makeText(AlipayClientActivity.this, getString(R.string.pay_error), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AlipayClientActivity.this, getString(R.string.alipay_system_exception), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    };


    public boolean isStrNull(String str) {
        if (str == null || "".equals(str) || "null".equals(str)
                || "default".equals(str) || "undefined".equals(str)) {
            return true;
        }
        return false;
    }

}
