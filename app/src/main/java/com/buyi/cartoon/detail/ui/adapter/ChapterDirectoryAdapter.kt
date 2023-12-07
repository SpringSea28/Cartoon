package com.buyi.cartoon.detail.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.buyi.cartoon.databinding.ChapterDirectoryItemBinding
import com.buyi.cartoon.databinding.DetailChapterItemBinding
import com.buyi.cartoon.http.bean.CartoonDetailBean
import com.buyi.cartoon.main.utils.ConstantApp

class ChapterDirectoryAdapter : RecyclerView.Adapter<ChapterDirectoryAdapter.ItemVh>() {
    private val TAG = ChapterDirectoryAdapter::class.java.simpleName

    val srcArray = ArrayList<CartoonDetailBean.ChapterInfo>()

    var onItemClickListener: ((position: Int,CartoonDetailBean.ChapterInfo)->Unit) = { _,_ -> }

    var reading:Int? = null

    var sort = ConstantApp.SORT_UP

    fun setData(data:List<CartoonDetailBean.ChapterInfo>?){
        if(data == null){
            return
        }
        srcArray.clear()
        srcArray.addAll(data)
        if (sort == ConstantApp.SORT_UP){
            srcArray.sortBy { chapterInfo -> chapterInfo.id }
        }else {
            srcArray.sortByDescending { chapterInfo -> chapterInfo.id }
        }
        notifyDataSetChanged()
    }

    fun updateReadingChapter(reading:Int){
        this.reading = reading
        notifyDataSetChanged()
    }

    fun updateSort(sort:Int){
        this.sort = sort
        if (sort == ConstantApp.SORT_UP){
            srcArray.sortBy { chapterInfo -> chapterInfo.id }
        }else {
            srcArray.sortByDescending { chapterInfo -> chapterInfo.id }
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            ChapterDirectoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        holder.binding.tvReading.setOnClickListener {
            onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])
        }

        holder.binding.tvChapter.isSelected = reading == srcArray[position].id

    }

    override fun getItemCount(): Int {
        return srcArray.size
    }



    class ItemVh(val binding : ChapterDirectoryItemBinding) : ViewHolder(binding.root){

    }
}