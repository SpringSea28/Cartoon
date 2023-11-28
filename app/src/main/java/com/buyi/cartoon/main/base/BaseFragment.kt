package com.buyi.cartoon.main.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T:ViewBinding> : Fragment() {

    protected lateinit var binding: T
    protected abstract fun getBindingView(inflater: LayoutInflater,
                                          container: ViewGroup?):T
    protected abstract fun initAllMembersData(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getBindingView(inflater,container)
        initAllMembersData(savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}