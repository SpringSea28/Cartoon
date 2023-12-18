package com.buyi.cartoon.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.buyi.cartoon.R
import com.buyi.cartoon.collect.ui.CollectFragment
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.home.ui.HomeFragment
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.my.ui.MyFragment
import com.buyi.cartoon.my.ui.YoungActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tencent.mmkv.MMKV


class MainActivity : BaseActivity<ActivityMainBinding>() {

    var homeFragment:HomeFragment?= null
    var collectFragment:CollectFragment?= null
    var myFragment: MyFragment?= null
    var CurrentFragment: Fragment? = null

    override val TAG: String
        get() = MainActivity::class.simpleName!!

    override fun getBindingView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null
        (navView.getChildAt(0) as? ViewGroup)?.children?.forEach { it.setOnLongClickListener { true } }

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        navView.setupWithNavController(navController)
        initFragment(savedInstanceState)
        navView.menu.findItem(R.id.navigation_home).isChecked = true
        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home ->{
                    if (homeFragment == null) {
                        homeFragment = HomeFragment()
                    }
                    switchContent(CurrentFragment,homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_collect ->{
                    if (collectFragment == null) {
                        collectFragment = CollectFragment()
                    }
                    switchContent(CurrentFragment,collectFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_my ->{
                    if (myFragment == null) {
                        myFragment = MyFragment()
                    }
                    switchContent(CurrentFragment,myFragment)
                    return@setOnItemSelectedListener true
                }
            }

            return@setOnItemSelectedListener false
        }
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

    fun initFragment(savedInstanceState: Bundle?) {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            if (homeFragment == null) {
                homeFragment = HomeFragment()
            }
            CurrentFragment = homeFragment
            Log.i(TAG,"initFragment homeFragment")
            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, homeFragment!!,homeFragment!!::class.java.simpleName)
                .commit() //fragment parent layout id
        }
    }

    fun switchContent(from: Fragment?, to: Fragment?) {
        if (from == null || to == null) return
        if (from !== to) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            if (!to.isAdded) {
                //fragment parent layout id
                Log.i(TAG,"add fragment from ${from::class.java.simpleName} to ${to::class.java.simpleName}")
                fragmentTransaction.hide(from).add(R.id.nav_host_fragment_activity_main, to,to::class.java.simpleName)
                    .commit()
            } else {
                Log.i(TAG,"show fragment from ${from::class.java.simpleName} to ${to::class.java.simpleName}")
                fragmentTransaction.hide(from).show(to).commit()
            }
            CurrentFragment = to
        }
    }


}