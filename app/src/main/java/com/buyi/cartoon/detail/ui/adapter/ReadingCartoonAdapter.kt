package com.buyi.cartoon.detail.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.buyi.cartoon.databinding.ReadingItemBinding
import com.buyi.cartoon.http.bean.DemoReqData

class ReadingCartoonAdapter:PagingDataAdapter<DemoReqData.DataBean.DatasBean,
        ReadingCartoonAdapter.ItemVh>(object :
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

    var onItemClickListener: ((position: Int)->Unit) = { _ -> }


    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val datasBean = getItem(position)
        val index = position %8
        val context = holder.binding.root.context
        Glide.with(context)
            .load(testUrl[index])
            .into(holder.binding.imgCartoon)
        holder.binding.root.setOnClickListener {
            onItemClickListener.invoke(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = ReadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : ReadingItemBinding) : RecyclerView.ViewHolder(binding.root){

    }



    val testUrl = arrayOf(
        "https://img1.baidu.com/it/u=225041176,855892897&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422",
        "https://img2.baidu.com/it/u=1665640482,1824915280&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img0.baidu.com/it/u=3145022701,3943645695&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889",
        "https://img2.baidu.com/it/u=710071810,2487788150&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800",
        "https://img2.baidu.com/it/u=2526519423,798155762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img2.baidu.com/it/u=3574827082,3267311681&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800",
        "https://img0.baidu.com/it/u=3279376560,3507705273&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img2.baidu.com/it/u=2242228201,4108939845&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"
    )
}