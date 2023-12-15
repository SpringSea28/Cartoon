package com.buyi.cartoon.my.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.databinding.YoungItemBinding

class YoungDetailItemAdapter: RecyclerView.Adapter<YoungDetailItemAdapter.ItemVh>() {



    override fun onBindViewHolder(holder: ItemVh, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = YoungItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    override fun getItemCount(): Int {
        return 0
    }

    class ItemVh(val binding : YoungItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}