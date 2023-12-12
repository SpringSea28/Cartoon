package com.buyi.cartoon.collect.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.CollectContentItemBinding
import com.buyi.cartoon.db.bean.CollectBean
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.main.utils.TextColorExpandTools
import com.buyi.cartoon.main.utils.Tools

class CollectAdapterBackup:PagingDataAdapter<CollectBean,
        CollectAdapterBackup.ItemVh>(object :
    DiffUtil.ItemCallback<CollectBean>() {
    override fun areItemsTheSame(
        oldItem: CollectBean,
        newItem: CollectBean
    ): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: CollectBean,
        newItem: CollectBean
    ): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val collectBean = getItem(position) ?: return
        val context = holder.binding.root.context
        Glide.with(context)
            .load(collectBean.imgUrl)
            .into(holder.binding.imgCover)
        holder.binding.tvTitle.text = collectBean.name
        val lastReadingText = context.getString(R.string.collect_last_reading_content,
            collectBean.lastReadingChapter,
            collectBean.lastReadingChapterTitle)

        TextColorExpandTools.setPrompt(holder.binding.tvLastReading,lastReadingText,
            "${collectBean.lastReadingChapter}",R.color.main_color_pink)
        TextColorExpandTools.setPrompt(holder.binding.tvLastReading,lastReadingText,
            "${collectBean.lastReadingChapterTitle}",R.color.main_color_pink)

        val latestChapter = context.getString(R.string.collect_latest_content,collectBean.lastUpdateChapter)
        TextColorExpandTools.setPrompt(holder.binding.tvLatestChapter,latestChapter,
            "${collectBean.lastUpdateChapter}",R.color.main_color_pink)

        holder.binding.tvStatus.isSelected = collectBean.status == ConstantApp.CARTOON_STATUS_LOADING
        if(collectBean.status == ConstantApp.CARTOON_STATUS_LOADING){
            holder.binding.tvStatus.text = context.getString(R.string.collect_chapter_loading)
        }else{
            holder.binding.tvStatus.text = context.getString(R.string.collect_chapter_over)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = CollectContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : CollectContentItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    private fun addTextView(context:Context,text:String?,linearLayout: LinearLayout){
        val label1 = TextView(context)
        val layoutParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParam.marginEnd = Tools.dip2px(context,8.0f)
        val padding = Tools.dip2px(context,7.0f)
        label1.setPadding(padding,0,padding,0)
        label1.setTextColor(context.getColor(R.color.home_classify_nav_tab_text_inactive_col))
        label1.background = context.getDrawable(R.drawable.bg_9_stroke_fb709c)
        label1.gravity = Gravity.CENTER
        label1.text = text
        label1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)
        linearLayout.addView(label1,layoutParam)
    }
}