package com.buyi.cartoon.main.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    lateinit var bottomSheetDialog: BottomSheetDialog
    private var isTransparentBackground = false
    private var isCanceledOnTouchOutside = true
    private var isCanceledOnBack = true
    lateinit var binding: T

    fun showDialog(fm: FragmentManager) {
        val ft = fm.beginTransaction()
        ft.add(this, "")
        ft.commitAllowingStateLoss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return bottomSheetDialog
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(isCanceledOnTouchOutside())
        if (!isCanceledOnBack()) {
            dialog?.setOnKeyListener { _, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0 }
        }
        dialog?.setOnShowListener {
            val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)!!
            val coordinatorLayout = bottomSheet.parent as CoordinatorLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            coordinatorLayout.parent.requestLayout()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAllMembersData(view, savedInstanceState)
        initListener()
    }


    open fun initListener() {}

    @SuppressLint("AutoDispose")
    override fun onStart() {
        super.onStart()
        if (isTransparentBackground()) {
            dialog?.window?.setDimAmount(0.0f)
        }
        Observable.just("").observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                bottomSheetDialog.delegate.findViewById<View>(R.id.design_bottom_sheet)
                    ?.setBackgroundColor(0x00000000)
            }
    }

    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean) {
        isCanceledOnTouchOutside = canceledOnTouchOutside
    }

    fun setCanceledOnBack(canceledOnBack: Boolean) {
        isCanceledOnBack = canceledOnBack
    }

    open fun isTransparentBackground(): Boolean {
        return isTransparentBackground
    }

    open fun isCanceledOnTouchOutside(): Boolean {
        return isCanceledOnTouchOutside
    }

    open fun isCanceledOnBack(): Boolean {
        return isCanceledOnBack
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    protected abstract fun initAllMembersData(view: View?, savedInstanceState: Bundle?)
}