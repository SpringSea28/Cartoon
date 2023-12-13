package com.buyi.cartoon.account.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyi.cartoon.account.util.UserConstant
import com.buyi.cartoon.databinding.FragmentGenderBottomDialogBinding
import com.buyi.cartoon.main.base.BaseBottomSheetDialogFragment

class GenderBottomDialog : BaseBottomSheetDialogFragment<FragmentGenderBottomDialogBinding>() {

    var onGenderClick: ((sex: Int) -> Unit) = {}

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentGenderBottomDialogBinding {
        return FragmentGenderBottomDialogBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(view: View?, savedInstanceState: Bundle?) {
       binding.tvMale.setOnClickListener {
           onGenderClick.invoke(UserConstant.SEX_BOY)
           dismissAllowingStateLoss()
       }

        binding.tvFemale.setOnClickListener {
            onGenderClick.invoke(UserConstant.SEX_GIRL)
            dismissAllowingStateLoss()
        }

        binding.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}