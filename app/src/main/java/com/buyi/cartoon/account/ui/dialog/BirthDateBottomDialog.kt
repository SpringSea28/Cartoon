package com.buyi.cartoon.account.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyi.cartoon.databinding.FragmentDatePickBottomDialogBinding
import com.buyi.cartoon.main.base.BaseBottomSheetDialogFragment

class BirthDateBottomDialog : BaseBottomSheetDialogFragment<FragmentDatePickBottomDialogBinding>() {

    var onBirthdateSel: ((year: Int,month:Int,day:Int) -> Unit) = {_,_,_ -> }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDatePickBottomDialogBinding {
        return FragmentDatePickBottomDialogBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(view: View?, savedInstanceState: Bundle?) {
        binding.datePicker.init(2000,0,1){ view, year, monthOfYear, dayOfMonth ->
            onBirthdateSel.invoke(year,monthOfYear,dayOfMonth)
        }
    }
}