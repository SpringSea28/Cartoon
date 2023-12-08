package com.buyi.cartoon.collect.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.CollectContentItemBinding
import com.buyi.cartoon.db.CollectBean
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.main.utils.TextColorExpandTools

class CollectAdapter:RecyclerView.Adapter<
        CollectAdapter.ItemVh>() {

    private val TAG = CollectAdapter::class.java.simpleName

    var onItemClickListener: ((position: Int, CollectBean)->Unit) = { _, _ -> }


    //1. 声明 DiffUtil.ItemCallback 回调
    private val itemCallback = object : DiffUtil.ItemCallback<CollectBean>() {
        override fun areItemsTheSame(oldItem: CollectBean, newItem: CollectBean): Boolean {
            val same =  oldItem.id == newItem.id
            return same
        }

        override fun areContentsTheSame(oldItem: CollectBean, newItem: CollectBean): Boolean {
            val same = oldItem.name == newItem.name
                    && oldItem.lastReadingChapter == newItem.lastReadingChapter
                    && oldItem.lastReadingChapterTitle == newItem.lastReadingChapterTitle
                    && oldItem.lastUpdateChapter == newItem.lastUpdateChapter
                    && oldItem.status == newItem.status

            return same
        }

    }

    //2. 创建 AsyncListDiff 对象
    private val mDiffer = AsyncListDiffer<CollectBean>(this, itemCallback)

    fun submitList(collectList: List<CollectBean>) {
        //3. 提交新数据列表
        mDiffer.submitList(collectList)
    }




    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val collectBean = mDiffer.currentList[position]
        val context = holder.binding.root.context
        Glide.with(context)
            .load(collectBean.imgUrl)
            .centerCrop()
            .into(holder.binding.imgCover)
        holder.binding.tvTitle.text = collectBean.name
        val lastReadingText = context.getString(R.string.collect_last_reading_content,
            collectBean.lastReadingChapter,
            collectBean.lastReadingChapterTitle)

        TextColorExpandTools.setPrompt(holder.binding.tvLastReading,lastReadingText,
            R.color.main_color_pink,"${collectBean.lastReadingChapter}","${collectBean.lastReadingChapterTitle}")

        val latestChapter = context.getString(R.string.collect_latest_content,collectBean.lastUpdateChapter)
        TextColorExpandTools.setPrompt(holder.binding.tvLatestChapter,latestChapter,
            "${collectBean.lastUpdateChapter}",R.color.main_color_pink)

        holder.binding.tvStatus.isSelected = collectBean.status == ConstantApp.CARTOON_STATUS_LOADING
        if(collectBean.status == ConstantApp.CARTOON_STATUS_LOADING){
            holder.binding.tvStatus.text = context.getString(R.string.collect_chapter_loading)
        }else{
            holder.binding.tvStatus.text = context.getString(R.string.collect_chapter_over)
        }

        holder.binding.tvContinueRead.setOnClickListener {
            onItemClickListener.invoke(position,collectBean)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = CollectContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    class ItemVh(val binding : CollectContentItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

}