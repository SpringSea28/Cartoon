package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.buyi.cartoon.databinding.HomeItemClass2Binding
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean

class HomeItemClass2Adapter : RecyclerView.Adapter<HomeItemClass2Adapter.ItemVh>() {
    private val TAG = HomeItemClass2Adapter::class.java.simpleName

    val srcArray = ArrayList<CartoonSimpleInfoBean>()

    var onItemClickListener: ((position: Int)->Unit) = { _ -> }

    fun setData(data:List<CartoonSimpleInfoBean>?){
        if(data == null){
            return
        }
        srcArray.clear()
        srcArray.addAll(data)
        Log.e(TAG,"setData ${data.size}")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            HomeItemClass2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflate)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        holder.binding.tvTitle.text = srcArray[position].title
        Glide.with(holder.binding.root)
            .load(srcArray[position].imgUrl)
            .centerCrop()
            .into(holder.binding.imgCover)
        holder.binding.root.setOnClickListener {
            onItemClickListener.invoke(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return srcArray.size
    }



    class ItemVh(val binding : HomeItemClass2Binding) : ViewHolder(binding.root){

    }
}