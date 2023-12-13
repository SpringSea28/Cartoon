package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivitySettingYoungBinding
import com.buyi.cartoon.main.base.BaseActivity


class SettingYoungActivity : BaseActivity<ActivitySettingYoungBinding>() {

    override val TAG: String
        get() = SettingYoungActivity::class.simpleName!!


    override fun getBindingView(): ActivitySettingYoungBinding {
        return ActivitySettingYoungBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.setting_title)
        binding.title.imgBack.setOnClickListener { finish() }

        binding.tvEnter.setOnClickListener {
            secretLaunch.launch(Intent(this,SettingYoungSecretActivity::class.java))
        }


    }

    private fun initVm(){

    }

    private val secretLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val result = it.data?.getBooleanExtra(SettingYoungSecretActivity.EXTRA_SECRET_SET_RESULT,false)
            if(result == true){
                finish()
            }
        }
    }
}