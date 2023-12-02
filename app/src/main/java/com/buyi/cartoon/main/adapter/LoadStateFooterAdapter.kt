package com.example.pagingdatademo.mvvm.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class LoadStateFooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {

    private val TAG = LoadStateFooterAdapter::class.java.simpleName
    private var loadStateViewHolder: LoadStateViewHolder? =null

    //重写，增加判断逻辑
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {

        //原有逻辑，loading和error状态下显示footer
        val resultA = loadState is LoadState.Loading || loadState is LoadState.Error
        //新增判断是否到末尾
        val resultB = loadState is LoadState.NotLoading && loadState.endOfPaginationReached
        val result  = resultA || resultB
//
        return result
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        Log.d(TAG, "onBindViewHolder")
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val loadStateViewHolder = LoadStateViewHolder(parent, retry)
        this.loadStateViewHolder = loadStateViewHolder
        Log.d(TAG, "onCreateViewHolder")
        return loadStateViewHolder
    }

}