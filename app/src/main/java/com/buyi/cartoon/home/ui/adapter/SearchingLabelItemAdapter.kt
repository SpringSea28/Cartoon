package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.buyi.cartoon.databinding.HomeSearchHistoryLabelItemBinding

class SearchingLabelItemAdapter : RecyclerView.Adapter<SearchingLabelItemAdapter.ItemVh>() {
    private val TAG = SearchingLabelItemAdapter::class.java.simpleName

    val srcArray = ArrayList<String>()

    var onItemClickListener: ((position: Int,label:String)->Unit) = { _,_ -> }

    fun setData(data:List<String>?){
        if(data == null){
            return
        }
        srcArray.clear()
        srcArray.addAll(data.reversed())
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            HomeSearchHistoryLabelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflate)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        holder.binding.tvLabel.text = srcArray[position]

        holder.binding.root.setOnClickListener {

            onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])

        }

    }

    override fun getItemCount(): Int {
        return srcArray.size
    }



    class ItemVh(val binding : HomeSearchHistoryLabelItemBinding) : ViewHolder(binding.root){

    }
}