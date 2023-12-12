package com.buyi.cartoon.account.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivityBindingSexBinding
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.google.android.material.bottomnavigation.BottomNavigationView

class BindSexActivity : BaseActivity<ActivityBindingSexBinding>() {

    override val TAG: String
        get() = BindSexActivity::class.simpleName!!

    override fun getBindingView(): ActivityBindingSexBinding {
        return ActivityBindingSexBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {

        binding.imgBoy.setOnClickListener {

        }

        binding.imgGirl.setOnClickListener {

        }

        binding.tvUnknown.setOnClickListener {

        }

    }



    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
//            val intent = Intent()
//            intent.putExtra(ConstantApp.INTENT_CHAPTER,chapter)
//            intent.putExtra(ConstantApp.INTENT_CARTOON_ID,cartoonId)
//            setResult(RESULT_OK,intent)
            finish()
        }
    }
}