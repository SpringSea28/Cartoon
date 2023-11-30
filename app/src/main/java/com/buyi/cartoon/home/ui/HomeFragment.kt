package com.buyi.cartoon.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.FragmentHomeBinding
import com.buyi.cartoon.home.vm.HomeViewModel
import com.buyi.cartoon.main.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val TAG = HomeFragment::class.simpleName

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()

    }

    private fun initUi(){
        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null
        (navView.getChildAt(0) as? ViewGroup)?.children?.forEach { it.setOnLongClickListener { true } }
        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_classify ->{ Log.e(TAG,"classify")}
                R.id.navigation_ranking ->{Log.e(TAG,"ranking")}
                R.id.navigation_boy ->{Log.e(TAG,"boy")}
                R.id.navigation_girl ->{Log.e(TAG,"girl")}
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initVm(){

    }
}

