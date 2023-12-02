package com.example.pagingdatademo.mvvm.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.DataListItemBottomBinding

class LoadStateViewHolder(parent: ViewGroup, var retry: () -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.data_list_item_bottom, parent, false)
) {
    private val TAG = LoadStateViewHolder::class.java.simpleName
    val context:Context = parent.context
    var itemLoadStateBindingUtil: DataListItemBottomBinding = DataListItemBottomBinding.bind(itemView)

    fun bindState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {
                itemLoadStateBindingUtil.tvBottom.visibility = View.VISIBLE
                itemLoadStateBindingUtil.tvBottom.text = context.getString(R.string.data_list_error_retry)
                itemLoadStateBindingUtil.tvBottom.setOnClickListener {
                    retry()
                }
                Log.d(TAG, "error")
            }
            is LoadState.Loading -> {
                Log.d(TAG, "Loading")
                itemLoadStateBindingUtil.tvBottom.visibility = View.GONE
            }
            is LoadState.NotLoading -> {
                Log.d(TAG, "NotLoading")
                itemLoadStateBindingUtil.tvBottom.visibility = View.VISIBLE
                itemLoadStateBindingUtil.tvBottom.text = context.getString(R.string.data_list_no_more_data)
                itemLoadStateBindingUtil.tvBottom.setOnClickListener {  }
            }
            else -> {

            }
        }

    }
}