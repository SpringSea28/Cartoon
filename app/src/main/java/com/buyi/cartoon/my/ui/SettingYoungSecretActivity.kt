package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivitySettingYoungSecretBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.tencent.mmkv.MMKV


class SettingYoungSecretActivity : BaseActivity<ActivitySettingYoungSecretBinding>() {

    override val TAG: String
        get() = SettingYoungSecretActivity::class.simpleName!!

    private var secretFirst:String? = null
    private var secretSecond:String? = null
    private var step = 1


    override fun getBindingView(): ActivitySettingYoungSecretBinding {
        return ActivitySettingYoungSecretBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.setting_title)
        binding.title.imgBack.setOnClickListener { finish() }

        binding.edtSecret.setOnTextChangeListener{text,isComplet ->

        }

        binding.tvNext.setOnClickListener {
            val isComplete = binding.edtSecret.text.toString().length == binding.edtSecret.maxLength
            if(isComplete){
                if(step == 1) {
                    secretFirst = binding.edtSecret.text.toString()
                    binding.tvSecret.text = getString(R.string.setting_young_secret_confirm)
                    binding.edtSecret.setText("")
                    step = 2
                }else if(step == 2){
                    secretSecond = binding.edtSecret.text.toString()
                    if(secretFirst == secretSecond){
                        MMKV.defaultMMKV().putString(ConstantApp.KEY_YOUNG_SECRET,secretSecond)
                        goYoung()
                        val intent = Intent()
                        intent.putExtra(EXTRA_SECRET_SET_RESULT,true)
                        setResult(RESULT_OK,intent)
                        finish()
                    }else{
                        Toast.makeText(this,
                            getString(R.string.setting_young_secret_different),
                        Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    private fun initVm(){

    }

    private fun goYoung(){
        startActivity(Intent(this,YoungActivity::class.java))
    }

    companion object{
        const val EXTRA_SECRET_SET_RESULT = "EXTRA_SECRET_SET_RESULT"
    }

}