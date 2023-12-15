package com.buyi.cartoon.wxapi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.buyi.cartoon.R
import com.buyi.cartoon.account.ui.LoginActivity
import com.buyi.cartoon.account.ui.PersonInfoBindWxActivity
import com.buyi.cartoon.detail.ui.CartoonDetailActivity
import com.buyi.cartoon.main.CartoonApp
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference

class WXEntryActivity : Activity(), IWXAPIEventHandler {

    private val TAG = "MicroMsg.WXEntryActivity"
    private var handler: MyHandler? = null
    private var api: IWXAPI? = null

    private class MyHandler(wxEntryActivity: WXEntryActivity) : Handler() {
        private val wxEntryActivityWeakReference: WeakReference<WXEntryActivity>

        init {
            wxEntryActivityWeakReference = WeakReference(wxEntryActivity)
        }

        override fun handleMessage(msg: Message) {
            val tag = msg.what
            when (tag) {
                NetworkUtil.GET_TOKEN -> {
                    val data = msg.data
                    var json: JSONObject? = null
                    try {
                        json = JSONObject(data.getString("result"))
                        val openId: String
                        val accessToken: String
                        val refreshToken: String
                        val scope: String
                        openId = json.getString("openid")
                        accessToken = json.getString("access_token")
                        refreshToken = json.getString("refresh_token")
                        scope = json.getString("scope")
                        var intent:Intent
                        if(CartoonApp.instance().wxFrom == 0) {
                            intent = Intent(
                                wxEntryActivityWeakReference.get(),
                                LoginActivity::class.java
                            )
                        }else{
                            intent = Intent(
                                wxEntryActivityWeakReference.get(),
                                PersonInfoBindWxActivity::class.java
                            )
                        }
                        intent.putExtra("openId", openId)
                        intent.putExtra("accessToken", accessToken)
                        intent.putExtra("refreshToken", refreshToken)
                        intent.putExtra("scope", scope)
                        intent.putExtra("result",true)
                        wxEntryActivityWeakReference.get()?.startActivity(intent)
                    } catch (e: JSONException) {
                        Log.e("MyHandler", e.message!!)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, ConstantWx.APP_ID, false)
        handler = MyHandler(this)
        try {
            val intent = intent
            api?.handleIntent(intent, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api?.handleIntent(intent, this)
    }

    override fun onReq(p0: BaseReq?) {

    }

    @SuppressLint("AutoDispose")
    override fun onResp(resp: BaseResp?) {
        var result = 0
        if(resp == null){
            fail()
            finish()
            return
        }

        result = when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> R.string.errcode_success
            BaseResp.ErrCode.ERR_USER_CANCEL -> R.string.errcode_cancel
            BaseResp.ErrCode.ERR_AUTH_DENIED -> R.string.errcode_deny
            BaseResp.ErrCode.ERR_UNSUPPORT -> R.string.errcode_unsupported
            else -> R.string.errcode_unknown
        }
        if(resp.errCode != BaseResp.ErrCode.ERR_OK){
            fail()
            finish()
            return
        }

//        Toast.makeText(this, getString(result) + ", type=" + resp.getType(), Toast.LENGTH_SHORT)
//            .show()
        if(resp.type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
            val respSend = resp as SendMessageToWX.Resp
            var intent:Intent = Intent(this@WXEntryActivity,
                CartoonDetailActivity::class.java)
            intent.putExtra("result",true)
            startActivity(intent)
        }

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            val authResp = resp as SendAuth.Resp
            val code = authResp.code
            var intent:Intent
            if(CartoonApp.instance().wxFrom == 0) {
                intent = Intent(this@WXEntryActivity,
                    LoginActivity::class.java)
                intent.putExtra("code", code)
                intent.putExtra("result",true)
                startActivity(intent)
            }else if(CartoonApp.instance().wxFrom == 1){
                intent = Intent(this@WXEntryActivity,
                    PersonInfoBindWxActivity::class.java)
                intent.putExtra("code", code)
                intent.putExtra("result",true)
                startActivity(intent)
            }else{
//                NetworkUtil.sendWxAPI(
//                handler, String.format(
//                    "https://api.weixin.qq.com/sns/oauth2/access_token?" +
//                            "appid=%s&secret=%s&code=%s&grant_type=authorization_code",
//                    "wxd930ea5d5a258f4f",
//                    "1d6d1d57a3dd063b36d917bc0b44d964",
//                    code
//                ), NetworkUtil.GET_TOKEN)
            }
        }
        finish()
    }

    private fun fail(){
        var intent:Intent? = null
        if(CartoonApp.instance().wxFrom == 0) {
            intent = Intent(this@WXEntryActivity,
                LoginActivity::class.java)
        }else if(CartoonApp.instance().wxFrom == 1){
            intent = Intent(this@WXEntryActivity,
                PersonInfoBindWxActivity::class.java)
        }else {
            intent = Intent(this@WXEntryActivity,
                CartoonDetailActivity::class.java)
        }
        intent?.putExtra("result",false)
        startActivity(intent)
    }

}