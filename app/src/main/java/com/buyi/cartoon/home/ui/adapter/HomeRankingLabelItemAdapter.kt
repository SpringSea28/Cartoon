package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.buyi.cartoon.databinding.HomeRankingLabelItemBinding
import com.buyi.cartoon.http.bean.ClassifyInfoBean

class HomeRankingLabelItemAdapter : RecyclerView.Adapter<HomeRankingLabelItemAdapter.ItemVh>() {
    private val TAG = HomeRankingLabelItemAdapter::class.java.simpleName

    val srcArray = ArrayList<ClassifyInfoBean>()
    var selId = -1

    var onItemClickListener: ((position: Int,selId:Int)->Unit) = { _,_ -> }

    fun setData(data:List<ClassifyInfoBean>?){
        if(data == null){
            return
        }
        srcArray.clear()
        srcArray.addAll(data)
        notifyDataSetChanged()
    }

    fun updateSel(selId:Int){
        this.selId = selId
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            HomeRankingLabelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflate)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        holder.binding.tvLabel.text = srcArray[position].classification
        holder.binding.tvLabel.isSelected = srcArray[position].id == selId

        holder.binding.root.setOnClickListener {
            srcArray[holder.bindingAdapterPosition].id?.let {
                onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition].id!!)
            }

        }

    }

    override fun getItemCount(): Int {
        return srcArray.size
    }



    class ItemVh(val binding : HomeRankingLabelItemBinding) : ViewHolder(binding.root){

    }
}