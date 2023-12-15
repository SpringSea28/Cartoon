package com.buyi.cartoon.my.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.databinding.YoungItemBinding
import com.buyi.cartoon.http.bean.CommentBean

class YoungItemAdapter:PagingDataAdapter<CommentBean.CommentReply,
        YoungItemAdapter.ItemVh>(object :
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = YoungItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : YoungItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}