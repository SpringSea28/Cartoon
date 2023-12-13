package com.buyi.cartoon.account.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserConstant
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.account.vm.BindSexVm
import com.buyi.cartoon.databinding.ActivityBindingSexBinding
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.google.android.material.bottomnavigation.BottomNavigationView

class BindSexActivity : BaseActivity<ActivityBindingSexBinding>() {

    override val TAG: String
        get() = BindSexActivity::class.simpleName!!

    private val bindSexVm:BindSexVm by viewModels()

    override fun getBindingView(): ActivityBindingSexBinding {
        return ActivityBindingSexBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback(this,onBackPress)

        initUi()
        initVm()
    }

    private fun initUi(){
        binding.imgBoy.setOnClickListener {
            bindSexVm.bindSex(UserConstant.SEX_BOY)
        }

        binding.imgGirl.setOnClickListener {
            bindSexVm.bindSex(UserConstant.SEX_GIRL)
        }

        binding.tvUnknown.setOnClickListener {
            bindSexVm.bindSex(UserConstant.SEX_UNKNOWN)
        }
    }

    private fun initVm(){
        bindSexVm.bindSexResultLd.observe(this){
            val intent = Intent()
            intent.putExtra(UserConstant.EXTRA_SEX,bindSexVm.sex)
            setResult(RESULT_OK,intent)
            onBackPress.handleOnBackPressed()
        }
    }

    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            finish()
        }
    }

}