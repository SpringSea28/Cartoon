package com.buyi.cartoon.detail.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.FragmentReadingSettingBottomDialogBinding
import com.buyi.cartoon.main.base.BaseBottomSheetDialogFragment

class ReadingSettingBottomDialog : BaseBottomSheetDialogFragment<FragmentReadingSettingBottomDialogBinding>() {

    var onCommentClick: (() -> Unit) = {}
    var onCollectClick: (() -> Unit) = {}
    var onShareClick: (() -> Unit) = {}
    var collectFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentReadingSettingBottomDialogBinding {
        return FragmentReadingSettingBottomDialogBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(view: View?, savedInstanceState: Bundle?) {
        if(collectFlag){
            binding.tvCollect.text = getString(R.string.reading_collect_cancel)
        }else{
            binding.tvCollect.text = getString(R.string.detail_collect)
        }
       binding.tvComment.setOnClickListener {
           onCommentClick.invoke()
           dismissAllowingStateLoss()
       }

        binding.tvCollect.setOnClickListener {
            onCollectClick.invoke()
            dismissAllowingStateLoss()
        }

        binding.tvShare.setOnClickListener {
            onShareClick.invoke()
            dismissAllowingStateLoss()
        }

        binding.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}