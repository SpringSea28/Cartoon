package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.buyi.cartoon.account.ui.LoginActivity
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.FragmentMyBinding
import com.buyi.cartoon.main.base.BaseFragment
import com.buyi.cartoon.main.utils.ConstantApp

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
        binding.tvLogin.setOnClickListener {
            loginLaunch.launch(Intent(context, LoginActivity::class.java))
        }

    }



    private val loginLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val chapter = it.data?.getIntExtra(ConstantApp.INTENT_LOGIN_RESULT,-1)
            if(chapter!= null && chapter > 0){

            }
        }
    }
}