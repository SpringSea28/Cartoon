package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivitySettingYoungSecretBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.tencent.mmkv.MMKV


class ExitYoungSecretActivity : BaseActivity<ActivitySettingYoungSecretBinding>() {

    override val TAG: String
        get() = ExitYoungSecretActivity::class.simpleName!!


    override fun getBindingView(): ActivitySettingYoungSecretBinding {
        return ActivitySettingYoungSecretBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = ""
        binding.title.imgBack.setOnClickListener { finish() }
        binding.tvSecret.text = getString(R.string.young_exit_secret)
        binding.tvSecretHint.text = getString(R.string.young_exit_secret_hint)

        binding.edtSecret.setOnTextChangeListener{text,isComplet ->

        }

        binding.tvNext.setOnClickListener {
            val isComplete = binding.edtSecret.text.toString().length == binding.edtSecret.maxLength
            if(isComplete){
                val secretFirst = MMKV.defaultMMKV().getString(ConstantApp.KEY_YOUNG_SECRET,null)
                val secretSecond = binding.edtSecret.text.toString()
                if(secretFirst == secretSecond){
                    MMKV.defaultMMKV().remove(ConstantApp.KEY_YOUNG_SECRET)
                    val intent = Intent()
                    intent.putExtra(EXTRA_EXIT_YOUNG_RESULT,true)
                    setResult(RESULT_OK,intent)
                    finish()
                }else{
                    Toast.makeText(this,
                        getString(R.string.young_exit_secret_error),
                    Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    private fun initVm(){

    }


    companion object{
        const val EXTRA_EXIT_YOUNG_RESULT = "EXTRA_EXIT_YOUNG_RESULT"
    }

}