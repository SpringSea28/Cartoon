package com.buyi.cartoon.my.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.buyi.cartoon.databinding.FragmentNoLoginBinding
import com.buyi.cartoon.main.base.BaseFragment

class NoLoginFragment : BaseFragment<FragmentNoLoginBinding>() {
    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentNoLoginBinding {
        return FragmentNoLoginBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {

    }

}