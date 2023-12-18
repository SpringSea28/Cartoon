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
import com.buyi.cartoon.detail.ui.CartoonDetailActivity
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
        binding.root.setOnClickListener {  }
        initSearch()
        initUiClassTab()
        initUiRecommend()
    }

    private fun initSearch(){
        binding.clTitle.setOnClickListener {
            startActivity(Intent(context,SearchActivity::class.java))
        }
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
                R.id.navigation_ranking ->{
                    startActivity(Intent(context,RankingActivity::class.java))
                }
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
        recommendAdapter.onItemClickListener = {position, cartoonSimpleInfoBean ->
            val intent = Intent(context,CartoonDetailActivity::class.java)
            intent.putExtra(ConstantApp.INTENT_CARTOON_DETAIL,cartoonSimpleInfoBean)
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = recommendAdapter

        recommendAdapter.onItemMoreClickListener = { position, homeRecommendItemBean ->
            val intent = Intent(context,HomeMoreActivity::class.java)
            intent.putExtra(ConstantApp.INTENT_HOME_MORE_ITEM,homeRecommendItemBean)
            startActivity(intent)
        }

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
        if(restore == true){
            return
        }
        homeVM.fetchRecommendList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("restore",true)
        super.onSaveInstanceState(outState)
        Log.e(TAG,"onSaveInstanceState")
    }

    private var restore:Boolean? = null
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        restore = savedInstanceState?.getBoolean("restore")
        Log.e(TAG,"onViewStateRestored")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG,"onHiddenChanged $hidden")
    }
}

