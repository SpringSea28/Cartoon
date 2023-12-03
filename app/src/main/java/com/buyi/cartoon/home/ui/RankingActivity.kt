package com.buyi.cartoon.home.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityRankingBinding
import com.buyi.cartoon.home.ui.adapter.HomeClassifyContentAdapter
import com.buyi.cartoon.home.ui.adapter.HomeRankingContentAdapter
import com.buyi.cartoon.home.ui.adapter.HomeRankingLabelItemAdapter
import com.buyi.cartoon.home.vm.RankingVM
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.main.base.BaseActivity
import com.example.pagingdatademo.mvvm.ui.adapter.LoadStateFooterAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RankingActivity : BaseActivity<ActivityRankingBinding>() {

    override val TAG: String
        get() = RankingActivity::class.simpleName!!

    private val rankingVM:RankingVM by viewModels()
    private val labelAdapter: HomeRankingLabelItemAdapter = HomeRankingLabelItemAdapter()


    private val contentAdapter = HomeRankingContentAdapter()
    private val footerAdapter = LoadStateFooterAdapter {
        contentAdapter.retry()
    }

    override fun getBindingView(): ActivityRankingBinding {
        return ActivityRankingBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
        getData()
    }

    private fun initUi(){
        setPadding(false)
        binding.title.tvTile.text = getString(R.string.home_nav_ranking)
        binding.title.imgBack.setOnClickListener { finish() }

        val labelList = ArrayList<ClassifyInfoBean>()
        labelList.add(ClassifyInfoBean(1,getString(R.string.home_ranking_label_1)))
        labelList.add(ClassifyInfoBean(2,getString(R.string.home_ranking_label_2)))
        labelList.add(ClassifyInfoBean(3,getString(R.string.home_ranking_label_3)))
        labelList.add(ClassifyInfoBean(4,getString(R.string.home_ranking_label_4)))
        labelList.add(ClassifyInfoBean(5,getString(R.string.home_ranking_label_5)))
        labelAdapter.setData(labelList)
        labelAdapter.updateSel(1)
        val gridLayoutManager = GridLayoutManager(this,5)
        binding.rcvLabel.layoutManager = gridLayoutManager
        binding.rcvLabel.setHasFixedSize(true)
        binding.rcvLabel.adapter = labelAdapter
        labelAdapter.onItemClickListener = { pos, selId ->
            labelAdapter.updateSel(selId)
        }


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