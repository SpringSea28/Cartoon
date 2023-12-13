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
import com.buyi.cartoon.account.ui.PersonInfoActivity
import com.buyi.cartoon.account.util.UserConstant
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
        updateLogin()
        binding.tvLogin.setOnClickListener {
            loginLaunch.launch(Intent(context, LoginActivity::class.java))
        }
        binding.tvPhone.setOnClickListener { goPersonInfo() }
        binding.tvNickname.setOnClickListener { goPersonInfo() }
        binding.imgHeader.setOnClickListener { goPersonInfo() }
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
            settingLaunch.launch(Intent(context,SettingActivity::class.java))
        }

        binding.tvVersion.text = getVersion()
    }

    private fun goPersonInfo(){
        val token = UserManager.getToken()
        if(token.isNullOrBlank()){
            return
        }else{
            infoLaunch.launch(Intent(requireContext(),PersonInfoActivity::class.java))
        }
    }

    private fun updateLogin(){
        val token = UserManager.getToken()
        if(token.isNullOrBlank()){
            binding.groupLogin.visibility = View.INVISIBLE
            binding.tvLogin.visibility = View.VISIBLE
            binding.imgHeader.setImageResource(R.mipmap.user_iamge_70px)
        }else{
            val userInfo = UserManager.getUserInfo()
            binding.tvPhone.text = userInfo?.phone
            binding.tvNickname.text = userInfo?.nickName
            if(userInfo?.gender == UserConstant.SEX_BOY){
                binding.imgHeader.setImageResource(R.mipmap.male)
            }else if(userInfo?.gender == UserConstant.SEX_GIRL){
                binding.imgHeader.setImageResource(R.mipmap.female)
            }else {
                binding.imgHeader.setImageResource(R.mipmap.user_iamge_70px)
            }
            binding.groupLogin.visibility = View.VISIBLE
            binding.tvLogin.visibility = View.INVISIBLE
        }
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
            val login = it.data?.getBooleanExtra(LoginActivity.EXTRA_LOGIN_RESULT,false)
            if(login == true){
                updateLogin()
            }
        }
    }

    private val settingLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val logout = it.data?.getBooleanExtra(SettingActivity.LOGIN_OUT,false)
            if(logout == true){
                updateLogin()
            }
            val accountCancel = it.data?.getBooleanExtra(SettingActivity.ACCOUNT_CANCEL,false)
            if(accountCancel == true){
                updateLogin()
            }
        }
    }

    private val infoLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val nickName = it.data?.getStringExtra(UserConstant.EXTRA_NICK_NAME)
            if(!nickName.isNullOrBlank()){
                binding.tvNickname.text = nickName
            }
        }
    }
}