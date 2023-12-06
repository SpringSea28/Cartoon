package com.buyi.cartoon.main.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.buyi.cartoon.databinding.ToastDialogBinding
import com.buyi.cartoon.main.base.BaseDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class ToastDialog : BaseDialog<ToastDialogBinding>() {
    var message: String? = null
    var timeOverListener: (() -> Unit)? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ToastDialogBinding {
        return ToastDialogBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(view: View?, savedInstanceState: Bundle?) {
        binding.tvMsg.text = message
        Observable.timer(1500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .to(
                AutoDispose.autoDisposable(
                    AndroidLifecycleScopeProvider.from(
                        requireActivity(),
                        Lifecycle.Event.ON_DESTROY
                    )
                )
            ).subscribe {
                timeOverListener?.invoke()
                dismissAllowingStateLoss() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        val window = dialog.window
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog
    }


}