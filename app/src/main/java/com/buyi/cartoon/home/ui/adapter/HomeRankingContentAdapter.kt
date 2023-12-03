package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.content.ClipData.Item
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
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.HomeClassifyContentItemBinding
import com.buyi.cartoon.databinding.HomeRankingContentItemBinding
import com.buyi.cartoon.http.bean.DemoReqData
import com.buyi.cartoon.main.utils.Tools

class HomeRankingContentAdapter:PagingDataAdapter<DemoReqData.DataBean.DatasBean,
        HomeRankingContentAdapter.ItemVh>(object :
    DiffUtil.ItemCallback<DemoReqData.DataBean.DatasBean>() {
    override fun areItemsTheSame(
        oldItem: DemoReqData.DataBean.DatasBean,
        newItem: DemoReqData.DataBean.DatasBean
    ): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DemoReqData.DataBean.DatasBean,
        newItem: DemoReqData.DataBean.DatasBean
    ): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val datasBean = getItem(position)
        holder.binding.tvContent.text = datasBean?.desc
        holder.binding.tvTitle.text = datasBean?.title
        holder.binding.tvRankingNumber.text = "${position+1}"
        val context = holder.binding.root.context
        if(position == 0){
            holder.binding.tvRankingNumber.setBackgroundResource(R.mipmap.num1)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.white))
        }else if(position ==1){
            holder.binding.tvRankingNumber.setBackgroundResource(R.mipmap.num2)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.white))
        }else if(position ==2){
            holder.binding.tvRankingNumber.setBackgroundResource(R.mipmap.num3)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.white))
        }else{
            holder.binding.tvRankingNumber.setBackgroundResource(R.drawable.bg_9_4d000000)
            holder.binding.tvRankingNumber.setTextColor(context.getColor(R.color.main_color_pink))
        }
        val text1 = datasBean?.author
        addTextView(context,text1,holder.binding.llLabel)
        val text2 = datasBean?.chapterName
        addTextView(context,text2,holder.binding.llLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = HomeRankingContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    class ItemVh(val binding : HomeRankingContentItemBinding) : RecyclerView.ViewHolder(binding.root){

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