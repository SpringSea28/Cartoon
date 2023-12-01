package com.buyi.cartoon.home.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.FragmentHomeBinding
import com.buyi.cartoon.home.ui.adapter.HomeItemClass1Adapter
import com.buyi.cartoon.home.vm.ClassifyVM
import com.buyi.cartoon.home.vm.HomeVM
import com.buyi.cartoon.main.base.BaseFragment
import com.buyi.cartoon.main.utils.ConstantApp
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val TAG = HomeFragment::class.simpleName
    private val homeVM:HomeVM by viewModels()
    private var recommendAdapter: HomeItemClass1Adapter = HomeItemClass1Adapter()

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
                R.id.navigation_classify ->{
                    startActivity(Intent(context,ClassifyActivity::class.java))
                }
                R.id.navigation_ranking ->{Log.e(TAG,"ranking")}
                R.id.navigation_boy ->{
                    val intent = Intent(context,ClassifyActivity::class.java)
                    intent.putExtra(ConstantApp.INTENT_CLASSIFY_TYPE,ClassifyVM.TYPE_BOY)
                    startActivity(intent)
                }
                R.id.navigation_girl ->{
                    val intent = Intent(context,ClassifyActivity::class.java)
                    intent.putExtra(ConstantApp.INTENT_CLASSIFY_TYPE,ClassifyVM.TYPE_GIRL)
                    startActivity(intent)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initUiRecommend(){
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = recommendAdapter

        binding.refreshLayout.setOnRefreshListener {
            homeVM.fetchRecommendList()
        }
    }

    private fun initVm(){
        recommendAdapter.setVm(homeVM)
        homeVM.recommendItemBeanListLD.observe(this){
            recommendAdapter.setData(it)
        }
        homeVM.recommendItemBeanLD.observe(this){
            recommendAdapter.updateItem(it)
        }
        homeVM.refreshFinishLD.observe(this){
            binding.refreshLayout.finishRefresh()
            if(it){
                binding.scrollView.visibility = View.VISIBLE
                binding.networkError.root.visibility = View.GONE
            }else{
                binding.scrollView.visibility = View.GONE
                binding.networkError.root.visibility = View.VISIBLE
            }
        }
    }

    private fun fetchData(){
        homeVM.fetchRecommendList()
    }
}

