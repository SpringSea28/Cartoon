package com.buyi.cartoon.account.vm

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.wxapi.ConstantWx
import com.buyi.cartoon.wxapi.bean.WxUserInfoBean
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WxVm(application: Application) : AndroidViewModel(application) {


    val loginResultLd = MutableLiveData<Boolean>()

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    val APP_ID = ConstantWx.APP_ID
    // IWXAPI 是第三方app和微信通信的openApi接口
    var api: IWXAPI? = null
    var wxUserInfoBean: WxUserInfoBean?= null

    var loggingIn = false

    override fun onCleared() {
        Log.i(TAG, "onCleared")
        super.onCleared()
    }

    fun regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(getApplication(), APP_ID, true)

        // 将应用的appId注册到微信
        api?.registerApp(APP_ID)

        //建议动态监听微信启动广播进行注册到微信
        getApplication<Application>().registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // 将该app注册到微信
                api?.registerApp(APP_ID)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }

    fun isWxInstalled():Boolean{
        return api?.isWXAppInstalled ?:false
    }



    fun login(){
        // send oauth request
        loggingIn = true
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "com.cchip.desheng"
        api?.sendReq(req)
    }

    fun accessTokenGet(intent: Intent ){
        val code = intent.getStringExtra("code")
        if(code == null){
            if(loggingIn)
                loginResultLd.postValue(false)
            return
        }
//        HttpReqManager.instance.wxLogin(code)
//            .subscribe(object : CustomObserver<WxLoginHttpBean>() {
//                override fun onCustomNext(result: WxLoginHttpBean) {
//                    if(result.code == 200) {
//                        this@WxVm.wxUserInfoBean = result.data
//                        if(wxUserInfoBean == null){
//                            loginResultLd.postValue(false)
//                        }else{
//                            loggingIn = false
//                            UserManager.saveWxInfo(wxUserInfoBean!!)
//                            loginResultLd.postValue(true)
//                        }
//                    }else{
//                        loginResultLd.postValue(false)
//                    }
//                }
//
//                override fun onCustomComplete(hasError: Boolean, e: Throwable?) {
//                    if(hasError) {
//                        loginResultLd.postValue(false)
//                    }
//                }
//            })
    }

    fun showUrlToSession(){
        val webpage = WXWebpageObject()
        webpage.webpageUrl = "http://www.qq.com"
        val msg = WXMediaMessage(webpage)
        msg.title =
            "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long"
        msg.description =
            "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long"

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("webpage")
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneSession
        api?.sendReq(req)
    }

    fun showUrlToPyq(){
        val webpage = WXWebpageObject()
        webpage.webpageUrl = "http://www.qq.com"
        val msg = WXMediaMessage(webpage)
        msg.title =
            "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long"
        msg.description =
            "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long"

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("webpage")
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneTimeline
        api?.sendReq(req)
    }

    private fun buildTransaction(type: String?): String? {
        return if (type == null) System.currentTimeMillis()
            .toString() else type + System.currentTimeMillis()
    }


    companion object {
        private val TAG = WxVm::class.java.simpleName
    }
}