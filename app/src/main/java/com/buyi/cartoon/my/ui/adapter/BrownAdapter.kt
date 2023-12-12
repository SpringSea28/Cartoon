package com.buyi.cartoon.my.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.collect.data.CollectUpdateBean
import com.buyi.cartoon.databinding.CollectContentItemBinding
import com.buyi.cartoon.db.bean.BrownBean
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.main.utils.TextColorExpandTools

class BrownAdapter:RecyclerView.Adapter<
        BrownAdapter.ItemVh>() {

    private val TAG = BrownAdapter::class.java.simpleName

    var onItemClickListener: ((position: Int, BrownBean)->Unit) = { _, _ -> }


    //1. 声明 DiffUtil.ItemCallback 回调
    private val itemCallback = object : DiffUtil.ItemCallback<BrownBean>() {
        override fun areItemsTheSame(oldItem: BrownBean, newItem: BrownBean): Boolean {
            val same =  oldItem.id == newItem.id
            return same
        }

        override fun areContentsTheSame(oldItem: BrownBean, newItem: BrownBean): Boolean {
            val same = oldItem.name == newItem.name
                    && oldItem.lastReadingChapter == newItem.lastReadingChapter
                    && oldItem.lastReadingChapterTitle == newItem.lastReadingChapterTitle
                    && oldItem.lastUpdateChapter == newItem.lastUpdateChapter
                    && oldItem.status == newItem.status
            return same
        }

    }

    //2. 创建 AsyncListDiff 对象
    private val mDiffer = AsyncListDiffer<BrownBean>(this, itemCallback)

    fun submitList(collectList: List<BrownBean>) {
        //3. 提交新数据列表
        mDiffer.submitList(collectList)
    }




    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val BrownBean = mDiffer.currentList[position]
        val context = holder.binding.root.context
        Glide.with(context)
            .load(BrownBean.imgUrl)
            .centerCrop()
            .into(holder.binding.imgCover)
        holder.binding.tvTitle.text = BrownBean.name
        val lastReadingText = context.getString(R.string.collect_last_reading_content,
            BrownBean.lastReadingChapter,
            BrownBean.lastReadingChapterTitle)

        TextColorExpandTools.setPrompt(holder.binding.tvLastReading,lastReadingText,
            R.color.main_color_pink,
            "${BrownBean.lastReadingChapter}",
            "${BrownBean.lastReadingChapterTitle}")

        val latestChapter = context.getString(R.string.collect_latest_content,BrownBean.lastUpdateChapter)
        TextColorExpandTools.setPrompt(holder.binding.tvLatestChapter,latestChapter,
            "${BrownBean.lastUpdateChapter}",R.color.main_color_pink)

        holder.binding.tvStatus.isSelected = BrownBean.status == ConstantApp.CARTOON_STATUS_LOADING
        if(BrownBean.status == ConstantApp.CARTOON_STATUS_LOADING){
            holder.binding.tvStatus.text = context.getString(R.string.collect_chapter_loading)
        }else{
            holder.binding.tvStatus.text = context.getString(R.string.collect_chapter_over)
        }

        holder.binding.tvContinueRead.setOnClickListener {
            onItemClickListener.invoke(position,BrownBean)
        }
    }

    override fun onBindViewHolder(holder: ItemVh, position: Int, payloads: MutableList<Any>) {
        if(!payloads.isEmpty()){
            val BrownBean = mDiffer.currentList[position]
            val context = holder.binding.root.context
            val payload = payloads.get(0) as CollectUpdateBean<*>
            if(payload.type == CollectUpdateBean.TYPE_READING_CHAPTER){
                val lastReadingText = context.getString(R.string.collect_last_reading_content,
                    payload.content as Int,
                    BrownBean.lastReadingChapterTitle)
                TextColorExpandTools.setPrompt(holder.binding.tvLastReading,lastReadingText,
                    R.color.main_color_pink,
                    "${BrownBean.lastReadingChapter}",
                    "${BrownBean.lastReadingChapterTitle}")
            }
        }else{
            onBindViewHolder(holder,position)
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