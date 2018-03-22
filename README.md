# Android_Modules

Android资源公共库

SlideDeailslibrary 仿淘宝商品详情上拉加载更多

bannar 水平轮播

flipperview 垂直轮播图,支持文字，图片+文字上下轮播

lfrecyclerview 轻量级列表数据展示，自带下拉刷新和上拉加载

pickerview 时间选择器

thirdpaylibrary 第三方支付封装,自带支付宝和微信jar,注意其他地方冲突
  支付模块说明： 收银台(自己搭建)  -->  调用支付接口，生成第三方所需参数（余额支付直接返回） -->  第三方支付(thirdpaylibrary)  
               1):调用自己服务器支付接口，获取第三方相应参数，以带入thirdpaylibrary进行第三方SDK检验支付（余额支付直接返回）
			   2):将服务器返回参数，传入thirdpaylibrary，
			   传入方式：微信：Wechat wechat = new Wechat();
                               wechat.setAppid(wechatBean.getResult().getAppid());
                               wechat.setPartnerid(wechatBean.getResult().getPartnerid());
                               wechat.setPrepayid(wechatBean.getResult().getPrepayid());
                               wechat.setPackages(wechatBean.getResult().getPackageX());
                               wechat.setNoncestr(wechatBean.getResult().getNoncestr());
                               wechat.setTimestamp(wechatBean.getResult().getTimestamp());
                               wechat.setSign(wechatBean.getResult().getSign());
							   //需要封装对象带入（Wechat已在thirdpaylibrary申明）
                               Intent it = new Intent();
                               it.setClass(this, WechatPayActivity.class);
                               it.putExtra("wechat", wechat); //wechat微信所需的各种参数的对象
                               startActivityForResult(it, PayCode.REQUEST_CODE);
						 支付宝: 
						       Intent it = new Intent();
                               it.setClass(this, AlipayClientActivity.class);
                               it.putExtra("alipay", alipay); //alipay服务器返回的一长串加密串
                               startActivityForResult(it, PayCode.REQUEST_CODE);
					     银联：
                               Intent it = new Intent();
                               it.setClass(PaymentActivity.this, VISAHtmlActivity.class);
                               it.putExtra("visa_html", visaHtml);//visaHtml服务器返回的银联支付H5链接
                               startActivityForResult(it, PayCode.REQUEST_CODE);
			   3):回调: @Override
                        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                            super.onActivityResult(requestCode, resultCode, data);
                            if (requestCode == PayCode.REQUEST_CODE) {
                                if (resultCode == PayCode.RESULT_CODE_PAYMENT_SUCCEED) {//支付成功
                                    //订单支付成功，并跳转到支付成功界面
                                    setResult(resultCode);
                                } else if (resultCode == PayCode.RESULT_CODE_PAYMENT_CANCEL) {//支付取消
                                    //支付取消
                                    setResult(resultCode);
                                } else if (resultCode == PayCode.RESULT_CODE_PAYMENT_ERROR) { //支付失败
                                    //请重新购买
                                    setResult(resultCode);
                                }
                                finish();
                             }
                        }
						
						
						
						