package com.buyi.cartoon.home.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.HomeRankingContentItemBinding
import com.buyi.cartoon.http.bean.DemoReqData
import com.buyi.cartoon.main.utils.TextColorExpandTools
import com.buyi.cartoon.main.utils.Tools

class SearchContentAdapter:PagingDataAdapter<DemoReqData.DataBean.DatasBean,
        SearchContentAdapter.ItemVh>(object :
    DiffUtil.ItemCallback<DemoReqData.DataBean.DatasBean>() {
    override fun areItemsTheSame(
        oldItem: DemoReqData.DataBean.DatasBean,
        newItem: DemoReqData.DataBean.DatasBean
    ): Boolean {
        return false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DemoReqData.DataBean.DatasBean,
        newItem: DemoReqData.DataBean.DatasBean
    ): Boolean {
        return false
    }
}) {

    private var key:String? = null

    fun updateKey(key:String){
        this.key = key
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val datasBean = getItem(position)
        holder.binding.tvContent.text = datasBean?.desc
        holder.binding.tvTitle.text = datasBean?.title
        holder.binding.tvRankingNumber.text = "${position+1}"
        val context = holder.binding.root.context
        holder.binding.tvRankingNumber.visibility = View.GONE

        holder.binding.llLabel.removeAllViews()
        val text1 = datasBean?.author
        addTextView(context,text1,holder.binding.llLabel)
        val text2 = datasBean?.chapterName
        addTextView(context,text2,holder.binding.llLabel)
        if(!datasBean?.title.isNullOrEmpty() && !key.isNullOrEmpty()) {
            TextColorExpandTools.setPrompt(
                holder.binding.tvTitle,
                datasBean?.title!!, key!!, R.color.main_color_pink
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflater = HomeRankingContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflater)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
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