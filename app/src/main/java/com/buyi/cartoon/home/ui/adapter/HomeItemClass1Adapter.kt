package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.buyi.cartoon.databinding.HomeItemClass1Binding
import com.buyi.cartoon.home.bean.HomeRecommendItemBean
import com.buyi.cartoon.home.vm.HomeVM

class HomeItemClass1Adapter : RecyclerView.Adapter<HomeItemClass1Adapter.ItemVh>() {

    val srcArray = ArrayList<HomeRecommendItemBean>()
    lateinit var homeVM: HomeVM

    var onItemClickListener: ((position: Int)->Unit) = { _ -> }

    fun setData(data:List<HomeRecommendItemBean>){
        srcArray.clear()
        srcArray.addAll(data)
        notifyDataSetChanged()
    }

    fun setVm(homeVM: HomeVM){
        this.homeVM = homeVM
    }

    fun updateItem(item:HomeRecommendItemBean){
        var position = -1
        for (index in 0 until srcArray.size){
            if(srcArray[index].id == item.id){
                position = index
                srcArray[index].cartoonsList = item.cartoonsList
            }
        }
        if(position <0){
            return
        }
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            HomeItemClass1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflate)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        holder.binding.tvTitle.text = srcArray[position].title
        val context = holder.binding.root.context
        val gridLayoutManager = GridLayoutManager(context,4)
        val itemAdapter: HomeItemClass2Adapter = HomeItemClass2Adapter()
        holder.binding.recyclerView.layoutManager = gridLayoutManager
        holder.binding.recyclerView.setHasFixedSize(true)
        holder.binding.recyclerView.adapter = itemAdapter
        itemAdapter.setData(srcArray[position].cartoonsList)

        holder.binding.tvMore.setOnClickListener {

        }
        holder.binding.tvReplace.setOnClickListener {
            homeVM.fetchRecommendList(srcArray[holder.adapterPosition].id)
        }
    }

    override fun getItemCount(): Int {
        return srcArray.size
    }



    class ItemVh(val binding : HomeItemClass1Binding) : ViewHolder(binding.root){

    }
}