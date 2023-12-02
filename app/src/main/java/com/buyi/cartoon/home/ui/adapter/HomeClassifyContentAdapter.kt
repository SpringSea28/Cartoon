package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.databinding.HomeClassifyContentItemBinding
import com.buyi.cartoon.http.bean.DemoReqData

class HomeClassifyContentAdapter:PagingDataAdapter<DemoReqData.DataBean.DatasBean,
        HomeClassifyContentAdapter.ItemVh>(object :
    DiffUtil.ItemCallback<DemoReqData.DataBean.DatasBean>() {
    override fun areItemsTheSame(
        oldItem: DemoReqData.DataBean.DatasBean,
        newItem: DemoReqData.DataBean.DatasBean
    ): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DemoReqData.DataBean.DatasBean,
        newItem: DemoReqData.DataBean.DatasBean
    ): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val datasBean = getItem(position)
        holder.binding.tvContent.text = datasBean?.desc
        holder.binding.tvTitle.text = datasBean?.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = HomeClassifyContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : HomeClassifyContentItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}