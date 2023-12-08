package com.buyi.cartoon.collect.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.collect.ui.adapter.CollectAdapter
import com.buyi.cartoon.collect.vm.CollectVm
import com.buyi.cartoon.databinding.FragmentCollectBinding
import com.buyi.cartoon.main.base.BaseFragment

class CollectFragment : BaseFragment<FragmentCollectBinding>() {

    private val TAG = CollectFragment::class.simpleName!!
    private val collectVm : CollectVm by viewModels()
    private val contentAdapter = CollectAdapter()


    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCollectBinding {
        return FragmentCollectBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
        getData()
    }

    private fun initUi(){
        binding.title.tvTile.text = getString(R.string.collect_title)

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcvCartoon.layoutManager = linearLayoutManager
        binding.rcvCartoon.setHasFixedSize(true)
        binding.rcvCartoon.adapter = contentAdapter

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.resetNoMoreData()
            collectVm.refresh()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            collectVm.fetchCollect()
        }
    }

    private fun initVm(){
        collectVm.collectLoadResultLd.observe(this){
            binding.refreshLayout.finishRefresh()
            binding.refreshLayout.finishLoadMore(true)
            if(collectVm.collectList.isEmpty()){
                binding.layEmpty.root.visibility = View.VISIBLE
            }else{
                binding.layEmpty.root.visibility = View.GONE
            }

            val data = ArrayList(collectVm.collectList)
            contentAdapter.submitList(data)
        }

        collectVm.noMoreDataLd.observe(this){
            binding.refreshLayout.finishLoadMoreWithNoMoreData()
        }
    }

    private fun getData(){
        collectVm.fetchCollect()
    }

}