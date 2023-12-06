package com.buyi.cartoon.main.base

import androidx.viewbinding.ViewBinding
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialog<T : ViewBinding> : DialogFragment() {
    var onDismissListener: DialogInterface.OnDismissListener? = null
    var onClickListener: View.OnClickListener? = null
    private var isTransparentBackground = false
    var isCanceledOnTouchOutside = false
    lateinit var binding: T

    fun showDialog(fm: FragmentManager) {
        val ft = fm.beginTransaction()
        ft.add(this, "")
        ft.commitAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        if (!isCanceledOnTouchOutside) {
            dialog?.setOnKeyListener { _: DialogInterface?, keyCode: Int, event: KeyEvent ->
                keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0
            }
        }
        initAllMembersData(binding.root, savedInstanceState)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (isTransparentBackground) {
            dialog?.window?.setDimAmount(0.0f)
        }
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    protected abstract fun initAllMembersData(view: View?, savedInstanceState: Bundle?)
    override fun onDestroy() {
        onDismissListener?.onDismiss(null)
        onClickListener = null
        super.onDestroy()
    }
}