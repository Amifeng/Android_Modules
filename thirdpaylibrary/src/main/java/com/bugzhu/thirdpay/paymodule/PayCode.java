package com.bugzhu.thirdpay.paymodule;

/**
 * Created by Administrator on 2016/12/28.
 */

public class PayCode {

    //微信支付返回码
    public final static int ERR_OK=0;  //成功
    public final static int ERR_COMM=-1;//一般错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
    public final static int ERR_USER_CANCEL=-2; //用户取消

    //支付宝支付
    public final static int REQUEST_CODE=100;
    public final static int RESULT_CODE_SUCCESS=101;
    public final static int RESULT_CODE_ORDER=801;
    public final static int RESULT_CODE_PAYMENT_SUCCEED=802;
    public final static int RESULT_CODE_PAYMENT_PASSWORD=803;
    public final static int RESULT_CODE_PAYMENT_CANCEL=816;
    public final static int RESULT_CODE_PAYMENT_ERROR=824;
}
