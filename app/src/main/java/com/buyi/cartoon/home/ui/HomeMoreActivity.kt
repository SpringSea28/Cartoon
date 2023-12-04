package com.buyi.cartoon.home.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.databinding.ActivityHomeMoreBinding
import com.buyi.cartoon.home.bean.HomeRecommendItemBean
import com.buyi.cartoon.home.ui.adapter.HomeMoreContentAdapter
import com.buyi.cartoon.home.vm.RankingVM
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.example.pagingdatademo.mvvm.ui.adapter.LoadStateFooterAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeMoreActivity : BaseActivity<ActivityHomeMoreBinding>() {

    override val TAG: String
        get() = HomeMoreActivity::class.simpleName!!

    private var recommendItemBean:HomeRecommendItemBean? = null

    private val rankingVM:RankingVM by viewModels()


    private val contentAdapter = HomeMoreContentAdapter()
    private val footerAdapter = LoadStateFooterAdapter {
        contentAdapter.retry()
    }

    override fun getBindingView(): ActivityHomeMoreBinding {
        return ActivityHomeMoreBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        getIntentData()
        initUi()
        initVm()
        getData()
    }

    private fun getIntentData(){
        recommendItemBean = intent.getParcelableExtra(ConstantApp.INTENT_HOME_MORE_ITEM)
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = recommendItemBean?.title
        binding.title.imgBack.setOnClickListener { finish() }


        binding.rcvCartoon.layoutManager= LinearLayoutManager(this)
        binding.rcvCartoon.adapter = contentAdapter.withLoadStateFooter(footerAdapter)

        binding.refreshLayout.setOnRefreshListener{
            contentAdapter.refresh()
        }

        contentAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.Loading -> {
                    Log.d(TAG,"refresh: loading")
                }
                is LoadState.NotLoading -> {
                    Log.d(TAG,"refresh: NotLoading")
                    binding.refreshLayout.finishRefresh()
                }
                is LoadState.Error -> {Log.d(TAG,"refresh: Error")
                    binding.refreshLayout.finishRefresh(false)
                }
            }
            when(it.append){
                is LoadState.Loading -> {Log.d(TAG,"append: loading")}
                is LoadState.NotLoading -> { Log.d(TAG, "append: NotLoading") }
                is LoadState.Error -> {Log.d(TAG,"append: Error")
                }
            }
        }
    }


    private fun initVm(){

    }

    private fun getData(){

        val job = lifecycleScope.launch {
            rankingVM.flow.collectLatest {
                contentAdapter.submitData(it)
            }
        }
    }

}