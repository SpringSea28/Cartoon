package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivitySettingBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.dialog.ConfirmDialog
import com.buyi.cartoon.my.ui.dialog.AccountCancelDialog
import com.buyi.cartoon.my.vm.SettingVm

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override val TAG: String
        get() = SettingActivity::class.simpleName!!

    private val settingVm:SettingVm by viewModels()

    override fun getBindingView(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.setting_title)
        binding.title.imgBack.setOnClickListener { finish() }

        val token = UserManager.getToken()
        if(token.isNullOrBlank()){
            binding.rlAccountCancel.visibility = View.GONE
            binding.tvLoginOut.visibility = View.GONE
        }
        binding.tvLoginOut.setOnClickListener {
            loginOut()
        }
        binding.rlAccountCancel.setOnClickListener {
            accountCancel()
        }
    }

    private fun initVm(){
        settingVm.loginOutResultLd.observe(this){
            if(it){
                val intent = Intent()
                intent.putExtra(LOGIN_OUT,true)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
        settingVm.accountCancelResultLd.observe(this){
            if(it){
                val intent = Intent()
                intent.putExtra(ACCOUNT_CANCEL,true)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }
    private fun loginOut(){
        val dialog = ConfirmDialog()
        dialog.title = getString(R.string.setting_login_out_title)
        dialog.tvMsg = getString(R.string.setting_login_out_msg)
        dialog.leftListener = {
            settingVm.loginOut()
        }
        dialog.showDialog(supportFragmentManager)
    }

    private fun accountCancel(){
        val dialog = AccountCancelDialog()
        dialog.leftListener = {
            settingVm.accountCancel()
        }
        dialog.showDialog(supportFragmentManager)
    }

    companion object{
        const val LOGIN_OUT = "LOGIN_OUT"
        const val ACCOUNT_CANCEL = "ACCOUNT_CANCEL"
    }

}