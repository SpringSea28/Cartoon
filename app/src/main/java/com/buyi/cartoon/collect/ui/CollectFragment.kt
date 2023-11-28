package com.buyi.cartoon.collect.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.buyi.cartoon.collect.vm.CollectVm
import com.buyi.cartoon.databinding.FragmentDashboardBinding
import com.buyi.cartoon.main.base.BaseFragment

class CollectFragment : BaseFragment<FragmentDashboardBinding>() {

    private val TAG = CollectFragment::class.simpleName!!
    private val collectVm : CollectVm by viewModels()


    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()

    }

    private fun initUi(){

    }

    private fun initVm(){

    }

}