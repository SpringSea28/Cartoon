package com.buyi.cartoon.detail.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.databinding.CommentDetailReplyItemBinding
import com.buyi.cartoon.databinding.HomeClassifyContentItemBinding
import com.buyi.cartoon.http.bean.CommentBean
import com.buyi.cartoon.http.bean.DemoReqData

class CommentDetailItemAdapter:PagingDataAdapter<CommentBean.CommentReply,
        CommentDetailItemAdapter.ItemVh>(object :
    DiffUtil.ItemCallback<CommentBean.CommentReply>() {
    override fun areItemsTheSame(
        oldItem: CommentBean.CommentReply,
        newItem: CommentBean.CommentReply
    ): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: CommentBean.CommentReply,
        newItem: CommentBean.CommentReply
    ): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val datasBean = getItem(position)
        holder.binding.tvNickname.text = datasBean?.nickname
        holder.binding.tvDate.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = CommentDetailReplyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : CommentDetailReplyItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}