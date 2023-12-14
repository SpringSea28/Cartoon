package com.buyi.cartoon.detail.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.DetailCommentItemBinding
import com.buyi.cartoon.http.bean.CommentBean
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.main.utils.TextColorExpandTools
import com.buyi.cartoon.main.utils.Tools

class DetailCommentAdapter : RecyclerView.Adapter<DetailCommentAdapter.ItemVh>() {
    private val TAG = DetailCommentAdapter::class.java.simpleName

    val srcArray = ArrayList<CommentBean>()

    var onItemClickListener: ((position: Int,CommentBean)->Unit) = { _,_ -> }
    var onItemMoreClickListener: ((position: Int,CommentBean)->Unit) = { _,_ -> }
    var onItemLikeClickListener: ((position: Int,CommentBean,like:Boolean)->Unit) = { _,_,_ -> }


    fun setData(data:List<CommentBean>?){
        if(data == null){
            return
        }
        srcArray.addAll(data)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVh {
        val inflate =
            DetailCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemVh(inflate)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ItemVh, position: Int) {
        val commentBean = srcArray[position]
        val context = holder.binding.root.context
        Glide.with(context)
            .load(srcArray[position].imgUrl)
            .centerCrop()
            .error(R.mipmap.user_iamge_34px)
            .fallback(R.mipmap.user_iamge_34px)
            .into(holder.binding.imgHeader)
        holder.binding.tvNickname.text = commentBean.nickname
        holder.binding.tvDate.text = commentBean.date
        holder.binding.tvComment.text = commentBean.comment
        if(commentBean.likeNum!=null && commentBean.likeNum!!> 0){
            holder.binding.tvLikeNum.text = "${commentBean.likeNum}"
        }else{
            holder.binding.tvLikeNum.text = ""
        }
        holder.binding.imgLike.isSelected = commentBean.like == ConstantApp.CARTOON_COMMENT_LIKE

        holder.binding.imgLike.setOnClickListener {
            if(srcArray[position].like ==ConstantApp.CARTOON_COMMENT_LIKE){
                srcArray[position].like = ConstantApp.CARTOON_COMMENT_UNLIKE
                srcArray[position].likeNum = srcArray[position].likeNum?.minus(1)
            }else{
                srcArray[position].like = ConstantApp.CARTOON_COMMENT_LIKE
                srcArray[position].likeNum = srcArray[position].likeNum?.plus(1) ?:1
            }
            holder.binding.imgLike.isSelected =
                srcArray[position].like == ConstantApp.CARTOON_COMMENT_LIKE
            if(srcArray[position].likeNum!=null && srcArray[position].likeNum!!> 0){
                holder.binding.tvLikeNum.text = "${srcArray[position].likeNum}"
            }else{
                holder.binding.tvLikeNum.text = ""
            }

            onItemLikeClickListener.invoke(position,srcArray[position],
                srcArray[position].like == ConstantApp.CARTOON_COMMENT_LIKE)
        }

        holder.binding.root.setOnClickListener {
            Log.i(TAG,"Comment")
            onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])
        }

        holder.binding.llCommentReply.removeAllViews()
        val replyList = commentBean.commentReplyList
        if(replyList.isNullOrEmpty()){
            holder.binding.llCommentReply.visibility = View.GONE
            return
        }
        holder.binding.llCommentReply.visibility = View.VISIBLE
//        holder.binding.llCommentReply.setOnClickListener {
//            Log.e(TAG,"reply Comment")
//        }
        val text = "${replyList[0].nickname}:${replyList[0].reply}"
        val key = "${replyList[0].nickname}:"
        val addTextView = addTextView(context, text, holder.binding.llCommentReply)
        TextColorExpandTools.setPrompt(
            addTextView,
            text, key, R.color.main_color_pink
        )
        addTextView.setOnClickListener {
            Log.i(TAG,"Comment1")
            onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])
        }
        if(replyList.size > 1){
            val text = "${replyList[1].nickname}:${replyList[1].reply}"
            val key = "${replyList[1].nickname}:"
            val addTextView = addTextView(context, text, holder.binding.llCommentReply)
            TextColorExpandTools.setPrompt(
                addTextView,
                text, key, R.color.main_color_pink
            )
            addTextView.setOnClickListener {
                Log.i(TAG,"Comment2")
                onItemClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])
            }
        }

        run {
            val text = context.getString(R.string.detail_comment_number,replyList.size)
            val addTextView = addTextView(context, text, holder.binding.llCommentReply)
            TextColorExpandTools.setPrompt(
                addTextView,
                text, text, R.color.main_color_pink
            )
            addTextView.setOnClickListener {
                Log.i(TAG,"MORE")
                onItemMoreClickListener.invoke(holder.bindingAdapterPosition,srcArray[holder.bindingAdapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return srcArray.size
    }



    class ItemVh(val binding : DetailCommentItemBinding) : ViewHolder(binding.root){

    }

    private fun addTextView(context: Context, text:String?, linearLayout: LinearLayout):TextView{
        val textView = TextView(context)
        val layoutParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParam.bottomMargin = Tools.dip2px(context,5.0f)
        textView.setTextColor(context.getColor(R.color.detail_comment))
        textView.gravity = Gravity.START
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11f)
        linearLayout.addView(textView,layoutParam)
        return textView

    }
}