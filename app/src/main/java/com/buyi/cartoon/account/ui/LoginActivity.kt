package com.buyi.cartoon.account.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityLoginBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.TextColorExpandTools
import com.buyi.cartoon.my.ui.WebViewActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val TAG: String
        get() = LoginActivity::class.simpleName!!

    private var agree = false

    override fun getBindingView(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi(){
        updateAgree()
        binding.imgAgree.setOnClickListener {
            agree = !agree
            updateAgree()
        }
        TextColorExpandTools.setPromptWidthListener(binding.tvAgree,getString(R.string.login_agree),
            R.color.main_color_pink,
            getString(R.string.login_agree_key1),keyListener1,
            getString(R.string.login_agree_key2),keyListener2,
        )
        binding.imgClose.setOnClickListener { finish() }
    }


    private fun updateAgree(){
        binding.imgAgree.isSelected = agree
        if(agree){
            binding.clAccountSecret.alpha = 1f
            binding.clAccountSecret.interruptTouch = false
            binding.tvLogin.alpha = 1f
            binding.tvLogin.isClickable = true
        }else{
            binding.clAccountSecret.alpha = 0.5f
            binding.clAccountSecret.interruptTouch = true
            binding.tvLogin.alpha = 0.5f
            binding.tvLogin.isClickable = false
            binding.edtPhone.clearFocus()
            binding.edtCode.clearFocus()
        }
    }


    val keyListener1:View.OnClickListener = OnClickListener {
        WebViewActivity.startActivity(
            this,
            getString(R.string.login_agree_title_user_protocol),
            "https://www.baidu.com"
        )
    }

    val keyListener2:View.OnClickListener = OnClickListener {
        WebViewActivity.startActivity(
            this,
            getString(R.string.login_agree_title_privacy),
            "https://www.baidu.com"
        )

    }
}