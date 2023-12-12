package com.buyi.cartoon.my.ui

import android.os.Bundle
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivitySettingBinding
import com.buyi.cartoon.main.base.BaseActivity

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override val TAG: String
        get() = SettingActivity::class.simpleName!!

    override fun getBindingView(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.setting_title)
        binding.title.imgBack.setOnClickListener { finish() }
    }

}