package com.buyi.cartoon.detail.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyi.cartoon.databinding.FragmentShareBottomDialogBinding
import com.buyi.cartoon.main.base.BaseBottomSheetDialogFragment
import com.buyi.cartoon.main.utils.ConstantApp

class ShareBottomDialog : BaseBottomSheetDialogFragment<FragmentShareBottomDialogBinding>() {

    var onShareClick: ((shareWay: Int) -> Unit) = {}

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentShareBottomDialogBinding {
        return FragmentShareBottomDialogBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(view: View?, savedInstanceState: Bundle?) {
       binding.imgWechat.setOnClickListener {
           onShareClick.invoke(ConstantApp.SHARE_WECHAT)
           dismissAllowingStateLoss()
       }

        binding.imgFriendCircle.setOnClickListener {
            onShareClick.invoke(ConstantApp.SHARE_PYQ)
            dismissAllowingStateLoss()
        }

        binding.imgQq.setOnClickListener {
            onShareClick.invoke(ConstantApp.SHARE_QQ)
            dismissAllowingStateLoss()
        }

        binding.imgCopy.setOnClickListener {
            onShareClick.invoke(ConstantApp.SHARE_COPY)
            dismissAllowingStateLoss()
        }

        binding.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}