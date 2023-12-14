package com.buyi.cartoon.account.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.account.vm.WxVm
import com.buyi.cartoon.databinding.ActivityPersonInfoBindWxBinding
import com.buyi.cartoon.main.CartoonApp
import com.buyi.cartoon.main.base.BaseActivity

class PersonInfoBindWxActivity : BaseActivity<ActivityPersonInfoBindWxBinding>() {

    override val TAG: String
        get() = PersonInfoBindWxActivity::class.simpleName!!

    private val wxVm: WxVm by viewModels()

    override fun getBindingView(): ActivityPersonInfoBindWxBinding {
        return ActivityPersonInfoBindWxBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        regToWx()
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.person_title)
        binding.title.imgBack.setOnClickListener { finish() }

        val userInfo = UserManager.getUserInfo()
        binding.tvAccount.text = userInfo?.phone
        if(userInfo?.wechatOpenid.isNullOrBlank()){
            binding.rlAccountBind.setOnClickListener {
                onWxClick()
            }
            binding.tvAccountBind.text = getString(R.string.person_account_wx_go_bind)
        }else{
            binding.tvAccountBind.text = getString(R.string.person_account_wx_bind)
        }

    }

    private fun initVm(){

        wxVm.loginResultLd.observe(this){
//            if(it){
//                if(wxVm.wxUserInfoBean?.phoneBindFlag == true){
//                    val token = wxVm.wxUserInfoBean!!.token
//                    MMKV.defaultMMKV().encode(Constant.KEY_TOKEN, token)
//                    myVm.getUserInfo()
//                }else {
//                    goBinding()
//                }
//            }else{
//                Toast.makeText(this,getString(R.string.login_wx_fail), Toast.LENGTH_SHORT).show()
//            }
        }

    }





    private fun regToWx() {
        wxVm.regToWx()
    }

    private fun onWxClick(){
        if(!wxVm.isWxInstalled()){
            Toast.makeText(this,getString(R.string.login_wx_null),Toast.LENGTH_SHORT).show()
            return
        }
        CartoonApp.instance().wxFrom = 1
        wxVm.login()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val result = intent.getBooleanExtra("result", false)
        if(!result){
            if(wxVm.loggingIn)
                Toast.makeText(this,getString(R.string.login_wx_fail),Toast.LENGTH_SHORT).show()
            return
        }
        wxVm.accessTokenGet(intent)

    }

}