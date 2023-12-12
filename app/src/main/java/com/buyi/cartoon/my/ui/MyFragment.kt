package com.buyi.cartoon.my.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.buyi.cartoon.R
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
        binding.rlBrown.setOnClickListener {
            startActivity(Intent(context,BrownActivity::class.java))
        }
        binding.rlAboutUs.setOnClickListener {
            WebViewActivity.startActivity(
                context,
                getString(R.string.my_about_us),
                getString(R.string.url_about_us)
            )
        }
        binding.rlPrivacy.setOnClickListener {
            WebViewActivity.startActivity(
                context,
                getString(R.string.my_privacy),
                getString(R.string.url_privacy)
            )
        }
        binding.rlFeedback.setOnClickListener {
            WebViewActivity.startActivity(
                context,
                getString(R.string.my_privacy),
                getString(R.string.url_feedback)
            )
        }

        binding.imgSet.setOnClickListener {
            startActivity(Intent(context,SettingActivity::class.java))
        }

        binding.tvVersion.text = getVersion()
    }


    private fun getVersion(): String {
        return try {
            val info = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            "V" + info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
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