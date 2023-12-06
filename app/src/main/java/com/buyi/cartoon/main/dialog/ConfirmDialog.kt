package com.buyi.cartoon.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyi.cartoon.databinding.PrimaryConfirmDialogLayoutBinding
import com.buyi.cartoon.main.base.BaseDialog

class ConfirmDialog : BaseDialog<PrimaryConfirmDialogLayoutBinding>() {

    var rightListener: (() -> Unit)? = null
    var leftListener: (() -> Unit)? = null
    var tvMsg: String? = null
    var title:String? = null
    var leftStr:String? = null
    var rightStr:String? = null
    var leftColor = -1
    var rightColor = -1
    var gravity:Int? = null

    override fun getViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): PrimaryConfirmDialogLayoutBinding {
        return PrimaryConfirmDialogLayoutBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(view: View?, savedInstanceState: Bundle?) {
        tvMsg?.let {
            binding.tvMsg.visibility = View.VISIBLE
            binding.tvMsg.setText(it)
        }
        title?.let {
            binding.titleText.text = it
        }
        leftStr?.let {
            binding.tvLeft.setText(it)
        }
        rightStr?.let {
            binding.tvRight.text = it
        }
        gravity?.let {
            binding.tvMsg.gravity = it
        }
        if (leftColor != -1) {
            binding.tvLeft.setTextColor(leftColor)
        }
        if (rightColor != -1) {
            binding.tvRight.setTextColor(rightColor)
        }
        initListener()
    }

    fun setMsgGravity(gravity : Int){
        this.gravity = gravity
    }

    private fun initListener() {
        binding.tvLeft.setOnClickListener {
            leftListener?.invoke()
            dismissAllowingStateLoss()
        }
        binding.tvRight.setOnClickListener {
            rightListener?.invoke()
            dismissAllowingStateLoss()
        }

    }
}