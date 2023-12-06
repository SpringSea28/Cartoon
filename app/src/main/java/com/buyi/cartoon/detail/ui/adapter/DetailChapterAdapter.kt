package com.buyi.cartoon.detail.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.buyi.cartoon.databinding.DetailChapterItemBinding
import com.buyi.cartoon.http.bean.CartoonDetailBean

class DetailChapterAdapter : RecyclerView.Adapter<DetailChapterAdapter.ItemVh>() {
    private val TAG = DetailChapterAdapter::class.java.simpleName

    val srcArray = ArrayList<CartoonDetailBean.ChapterInfo>()

    var onItemClickListener: ((position: Int,CartoonDetailBean.ChapterInfo)->Unit) = { _,_ -> }

    var reading:Int? = null

    fun setData(data:List<CartoonDetailBean.ChapterInfo>?){
        if(data == null){
            return
        }
        srcArray.clear()
        srcArray.addAll(data)
        notifyDataSetChanged()
    }

    fun updateReadingChapter(reading:Int){
        this.reading = reading
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            DetailChapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflate)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        holder.binding.tvChapter.text = srcArray[position].name
        holder.binding.tvDate.text = srcArray[position].time
        Glide.with(holder.binding.root)
            .load(srcArray[position].imgUrl)
            .centerCrop()
            .into(holder.binding.imgCover)
        holder.binding.root.setOnClickListener {
            onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])
        }

        holder.binding.tvChapter.isSelected = reading == srcArray[position].id

    }

    override fun getItemCount(): Int {
        if(srcArray.size > 8){
            return 8
        }
        return srcArray.size
    }



    class ItemVh(val binding : DetailChapterItemBinding) : ViewHolder(binding.root){

    }
}