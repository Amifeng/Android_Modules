package com.bugzhu.thirdpay.paymodule;

/**
 * Created by Pashley on 2016/9/20.
 */
public enum PaywayType {
    WECHAT_PAY(1,"微信支付"),
    ALI_PAY(2,"支付宝支付"),
    BALANCE_PAY(3,"余额支付"),
    VISA_PAY(4,"银联支付");

    private int type;
    private String desc;

    PaywayType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
