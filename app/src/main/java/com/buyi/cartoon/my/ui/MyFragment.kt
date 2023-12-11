package com.buyi.cartoon.my.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.FragmentMyBinding
import com.buyi.cartoon.databinding.FragmentNoLoginBinding
import com.buyi.cartoon.main.base.BaseFragment

class MyFragment : BaseFragment<FragmentMyBinding>() {

    private val TAG = MyFragment::class.java.simpleName

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        val token = UserManager.getToken()
        if(token.isNullOrEmpty()){
            binding.groupLogin.visibility = View.INVISIBLE
            binding.tvLogin.visibility = View.VISIBLE
        }else{
            binding.groupLogin.visibility = View.VISIBLE
            binding.tvLogin.visibility = View.INVISIBLE
        }
    }

}