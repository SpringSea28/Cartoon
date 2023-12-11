package com.buyi.cartoon.my.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.buyi.cartoon.databinding.FragmentLoginedBinding
import com.buyi.cartoon.main.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginedBinding>() {
    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentLoginedBinding {
        return FragmentLoginedBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {

    }

}