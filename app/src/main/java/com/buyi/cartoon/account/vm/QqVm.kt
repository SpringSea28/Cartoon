package com.buyi.cartoon.account.vm

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.R
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.qqapi.ConstantQq
import com.buyi.cartoon.qqapi.bean.QqUserInfoBean
import com.tencent.connect.share.QQShare
import com.tencent.tauth.DefaultUiListener
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject

class QqVm(application: Application) : AndroidViewModel(application) {


    val shareResultLd = MutableLiveData<Boolean>()
    var errorMsg:String? = null

    var mTencent: Tencent? = null


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
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener)
    }


    private var qqShareListener: IUiListener = object : DefaultUiListener() {
        override fun onComplete(response: Any) {
            Log.e(TAG,"qq share suc:")
            shareResultLd.postValue(true)
        }

        override fun onError(e: UiError) {
            errorMsg = e.errorMessage
            Log.e(TAG, "qq share error:$errorMsg")
            shareResultLd.postValue(false)
        }

        override fun onCancel() {
            Log.e(TAG,"qq share cancel:")
            shareResultLd.postValue(false)
        }

    }



    fun shareQQ(activity: Activity){
        var mExtarFlag = 0x00
        mExtarFlag =  QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE

        val params = Bundle()

        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, ConstantApp.SHARE_URL)

        params.putString(QQShare.SHARE_TO_QQ_TITLE, activity.getString(R.string.app_name))
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getString(R.string.app_name))
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,  QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag)


        mTencent?.shareToQQ(activity, params, qqShareListener);
    }


    companion object {
        private val TAG = QqVm::class.java.simpleName
    }
}