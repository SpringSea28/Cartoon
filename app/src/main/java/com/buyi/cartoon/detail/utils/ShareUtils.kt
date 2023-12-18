package com.buyi.cartoon.detail.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.buyi.cartoon.R
import com.buyi.cartoon.account.vm.QqVm
import com.buyi.cartoon.account.vm.WxVm
import com.buyi.cartoon.detail.ui.dialog.ShareBottomDialog
import com.buyi.cartoon.main.CartoonApp
import com.buyi.cartoon.main.utils.ConstantApp
import com.tencent.connect.common.Constants

class ShareUtils(val activity:AppCompatActivity,val qqVm: QqVm,val wxVm: WxVm) {

    fun copy(){
        val cm: ClipboardManager = activity.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", "这里是要复制的文字")
        cm.setPrimaryClip(mClipData)
        Toast.makeText(activity,activity.getString(R.string.copy_suc), Toast.LENGTH_SHORT).show()
    }

    fun showWx(){
        if(!wxVm.isWxInstalled()){
            Toast.makeText(activity,activity.getString(R.string.login_wx_null), Toast.LENGTH_SHORT).show()
            return
        }
        CartoonApp.instance().wxFrom = 2
        wxVm.showUrlToSession()
    }

    fun showPyq(){
        if(!wxVm.isWxInstalled()){
            Toast.makeText(activity,activity.getString(R.string.login_wx_null), Toast.LENGTH_SHORT).show()
            return
        }
        CartoonApp.instance().wxFrom = 2
        wxVm.showUrlToPyq()
    }

    fun regToWx() {
        wxVm.regToWx()
    }


    fun onNewIntent(activity: AppCompatActivity,intent: Intent) {
        activity.setIntent(intent)
        val result = intent.getBooleanExtra("result", false)
        if(!result){
            Toast.makeText(activity,activity.getString(R.string.share_fail), Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(activity,activity.getString(R.string.share_suc), Toast.LENGTH_SHORT).show()
    }


    fun regToQQ() {
        qqVm.regToQq(activity)
        qqVm.shareResultLd.observe(activity){
            if(!it){
                Toast.makeText(activity,activity.getString(R.string.share_fail),Toast.LENGTH_SHORT).show()
                return@observe
            }
            Toast.makeText(activity,activity.getString(R.string.share_suc),Toast.LENGTH_SHORT).show()
        }
    }

    fun shareQQ(){
        qqVm.shareQQ(activity)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            qqVm.onActivityResultData(requestCode, resultCode, data)
        }
    }



    fun onShare(supportFragmentManager:FragmentManager){
        val dialog = ShareBottomDialog()
        dialog.onShareClick = {
            when(it){
                ConstantApp.SHARE_WECHAT -> showWx()
                ConstantApp.SHARE_PYQ -> showPyq()
                ConstantApp.SHARE_QQ -> {shareQQ()}
                ConstantApp.SHARE_COPY -> copy()
            }
        }
        dialog.show(supportFragmentManager,"share")
    }
}