package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.HomeClassifyContentItemBinding
import com.buyi.cartoon.databinding.HomeRankingContentItemBinding
import com.buyi.cartoon.http.bean.DemoReqData

class HomeRankingContentAdapter:PagingDataAdapter<DemoReqData.DataBean.DatasBean,
        HomeRankingContentAdapter.ItemVh>(object :
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
        holder.binding.tvRankingNumber.text = "${position+1}"
        val context = holder.binding.root.context
        if(position == 0){
            holder.binding.tvRankingNumber.setBackgroundResource(R.mipmap.num1)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.white))
        }else if(position ==1){
            holder.binding.tvRankingNumber.setBackgroundResource(R.mipmap.num2)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.white))
        }else if(position ==2){
            holder.binding.tvRankingNumber.setBackgroundResource(R.mipmap.num3)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.white))
        }else{
            holder.binding.tvRankingNumber.setBackgroundResource(R.drawable.bg_9_4d000000)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.main_color_pink))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = HomeRankingContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : HomeRankingContentItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}