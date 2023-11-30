package com.buyi.cartoon.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.FragmentHomeBinding
import com.buyi.cartoon.home.adapter.HomeItemClass1Adapter
import com.buyi.cartoon.home.vm.HomeVM
import com.buyi.cartoon.main.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val TAG = HomeFragment::class.simpleName
    private val homeVM:HomeVM by viewModels()
    private var recommendAdapter:HomeItemClass1Adapter = HomeItemClass1Adapter()

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
        fetchData()
    }

    private fun initUi(){
        initUiClassTab()
        initUiRecommend()
    }

    private fun initUiClassTab(){
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

    private fun initUiRecommend(){
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = recommendAdapter

    }

    private fun initVm(){
        recommendAdapter.setVm(homeVM)
        homeVM.recommendItemBeanList.observe(this){
            recommendAdapter.setData(it)
        }
    }

    private fun fetchData(){
        homeVM.fetchRecommendList()
    }
}

