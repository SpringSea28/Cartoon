package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.buyi.cartoon.databinding.HomeItemClass2BigBinding
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean

class HomeItemClass2AdapterBig3 : RecyclerView.Adapter<HomeItemClass2AdapterBig3.ItemVh>() {
    private val TAG = HomeItemClass2AdapterBig3::class.java.simpleName

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
            HomeItemClass2BigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
       if(srcArray.size > 6){
           return 6
       }
        return srcArray.size
    }



    class ItemVh(val binding : HomeItemClass2BigBinding) : ViewHolder(binding.root){

    }
}