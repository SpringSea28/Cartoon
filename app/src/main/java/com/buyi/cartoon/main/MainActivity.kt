package com.buyi.cartoon.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.my.ui.YoungActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tencent.mmkv.MMKV

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val TAG: String
        get() = MainActivity::class.simpleName!!

    override fun getBindingView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null
        (navView.getChildAt(0) as? ViewGroup)?.children?.forEach { it.setOnLongClickListener { true } }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val secretFirst = MMKV.defaultMMKV().getString(ConstantApp.KEY_YOUNG_SECRET,null)
        if(secretFirst.isNullOrBlank()) {

        }else{
            goYoung()
        }
    }

    private fun goYoung(){
        startActivity(Intent(this, YoungActivity::class.java))
        finish()
    }
}