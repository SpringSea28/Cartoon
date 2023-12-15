package com.buyi.cartoon.account.vm

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.qqapi.ConstantQq
import com.buyi.cartoon.qqapi.bean.QqUserInfoBean
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.tauth.DefaultUiListener
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONException
import org.json.JSONObject

class QqVm(application: Application) : AndroidViewModel(application) {


    val loginResultLd = MutableLiveData<Boolean>()

    var mTencent: Tencent? = null
    var qqUserInfoBean: QqUserInfoBean?= null

    var loggingIn = false

    override fun onCleared() {
        Log.i(TAG, "onCleared")
        super.onCleared()
    }

    fun regToQq(activity:Activity) {
        mTencent = Tencent.createInstance(
            ConstantQq.APP_ID,
            activity
        )

        Tencent.setIsPermissionGranted(true)
    }

    fun isQqInstalled():Boolean{
        return mTencent?.isQQInstalled(getApplication()) ?:false
    }


    fun onActivityResultData(requestCode: Int, resultCode: Int, data: Intent?){
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener)
    }

    fun login(activity:Activity){
        if(mTencent == null){
            return
        }
        if (mTencent!!.isSessionValid() == false) {
            val params = HashMap<String, Any>()
            params[Constants.KEY_SCOPE] = "all"
            loggingIn = true
            mTencent!!.login(activity, loginListener, params)
        } else {
            mTencent!!.logout(activity)
        }
    }


    private var loginListener: IUiListener = object : DefaultUiListener() {
        override fun onComplete(response: Any) {
            if (null == response) {
                if(loggingIn)
                    loginResultLd.postValue(false)
                return
            }
            val jsonResponse = response as JSONObject
            if (jsonResponse.length() == 0) {
                loginResultLd.postValue(false)
                return
            }
            doComplete(response)
        }

        override fun onError(e: UiError) {
            if(loggingIn)
                loginResultLd.postValue(false)
        }

        override fun onCancel() {
            if(loggingIn)
                loginResultLd.postValue(false)
        }

        fun doComplete(values: JSONObject?) {
            if(values == null) return
            initOpenidAndToken(values!!)
//            updateUserInfo()
        }
    }

    fun initOpenidAndToken(jsonObject: JSONObject) {
        try {
            val token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN)
            val expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN)
            val openId = jsonObject.getString(Constants.PARAM_OPEN_ID)
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                && !TextUtils.isEmpty(openId)
            ) {
                mTencent?.setAccessToken(token, expires)
                login(token)
//                mTencent?.setOpenId(openId)
            }
        } catch (e: Exception) {
            if(loggingIn)
                loginResultLd.postValue(false)
        }
    }

    private fun login(accessToken: String){
//        HttpReqManager.instance.qqLogin(accessToken)
//            .subscribe(object : CustomObserver<QqLoginHttpBean>() {
//                override fun onCustomNext(result: QqLoginHttpBean) {
//                    if(result.code == 200) {
//                        this@QqVm.qqUserInfoBean = result.data
//                        if(qqUserInfoBean == null){
//                            loginResultLd.postValue(false)
//                        }else{
//                            loggingIn = false
//                            UserManager.saveQqInfo(qqUserInfoBean!!)
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

    private fun updateUserInfo() {
        if (mTencent != null && mTencent!!.isSessionValid()) {
            val listener: IUiListener = object : DefaultUiListener() {
                override fun onError(e: UiError) {}
                override fun onComplete(response: Any) {
                    val response = response as JSONObject
                    qqUserInfoBean = QqUserInfoBean()
                    try {
//                        qqUserInfoBean!!.nickname = response.getString("nickname")
//                        qqUserInfoBean!!.headimgurl = response.getString("figureurl_qq_2")
//                        qqUserInfoBean!!.sex = response.getString("gender")
                        qqUserInfoBean?.loginId = mTencent?.openId
//                        UserManager.saveQqInfo(qqUserInfoBean!!)
                        loginResultLd.postValue(true)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onCancel() {}
            }
            val info = UserInfo(getApplication(), mTencent?.getQQToken())
            info.getUserInfo(listener)
        } else {

        }
    }


    companion object {
        private val TAG = QqVm::class.java.simpleName
    }
}