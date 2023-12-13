package com.buyi.cartoon.account.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserConstant
import com.buyi.cartoon.account.vm.LoginVerificationVm
import com.buyi.cartoon.account.vm.WxVm
import com.buyi.cartoon.databinding.ActivityLoginBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.dialog.ToastDialog
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.main.utils.TextColorExpandTools
import com.buyi.cartoon.my.ui.WebViewActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val TAG: String
        get() = LoginActivity::class.simpleName!!

    private var agree = false
    private val loginVerificationVm:LoginVerificationVm by viewModels()
    private val wxVm: WxVm by viewModels()

    override fun getBindingView(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        regToWx()
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        updateAgree()
        binding.imgAgree.setOnClickListener {
            agree = !agree
            updateAgree()
        }
        TextColorExpandTools.setPromptWidthListener(binding.tvAgree,getString(R.string.login_agree),
            R.color.main_color_pink,
            getString(R.string.login_agree_key1),keyListener1,
            getString(R.string.login_agree_key2),keyListener2,
        )
        binding.imgClose.setOnClickListener { finish() }
        binding.imgWechat.setOnClickListener { onWxClick() }
        binding.tvVerification.setOnClickListener { getVerification() }
        binding.tvLogin.setOnClickListener {
            login(binding.edtPhone.text.toString(),
                binding.edtCode.text.toString())
        }
    }

    private fun initVm(){
        loginVerificationVm.resetCountDownLd.observe(this){
            if(it){
                binding.tvVerification.text = getString(R.string.login_get_code)
            }
        }
        loginVerificationVm.countDownSecondLd.observe(this){
            binding.tvVerification.text = ""+it+"S"
        }
        loginVerificationVm.loginResultLd.observe(this){
            if(it){
                goBindingSex()
            }
        }

        loginVerificationVm.networkErrorDataLd.observe(this){
            val dialog = ToastDialog()
            dialog.message = getString(R.string.network_error)
            dialog.showDialog(supportFragmentManager)
        }
        loginVerificationVm.requestErrorLd.observe(this){
            val dialog = ToastDialog()
            dialog.message = it
            dialog.showDialog(supportFragmentManager)
        }
        loginVerificationVm.codeIdErrorLd.observe(this){
            val dialog = ToastDialog()
            dialog.message = getString(R.string.code_id_error)
            dialog.showDialog(supportFragmentManager)
        }

        wxVm.loginResultLd.observe(this){
//            if(it){
//                if(wxVm.wxUserInfoBean?.phoneBindFlag == true){
//                    val token = wxVm.wxUserInfoBean!!.token
//                    MMKV.defaultMMKV().encode(Constant.KEY_TOKEN, token)
//                    myVm.getUserInfo()
//                }else {
//                    goBinding()
//                }
//            }else{
//                Toast.makeText(this,getString(R.string.login_wx_fail), Toast.LENGTH_SHORT).show()
//            }
        }

    }

    private fun getVerification(){
        if(binding.edtPhone.text.toString().isNullOrEmpty()){
            Toast.makeText(this,R.string.login_phone_empty,Toast.LENGTH_SHORT).show()
            return
        }
        loginVerificationVm.getVerification(binding.edtPhone.text.toString())
    }

    private fun login(phone:String,verification:String){

        if(binding.edtPhone.text.toString().isNullOrEmpty()){
            Toast.makeText(this,R.string.login_phone_empty,Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.edtCode.text.toString().isNullOrEmpty()){
            Toast.makeText(this,R.string.login_phone_verification_empty,Toast.LENGTH_SHORT).show()
            return
        }

        loginVerificationVm.login(phone,verification)
    }


    private fun goBindingSex(){
        val intent = Intent(this,BindSexActivity::class.java)
        bindingSexLaunch.launch(intent)
    }


    private fun updateAgree(){
        binding.imgAgree.isSelected = agree
        if(agree){
            binding.clAccountSecret.alpha = 1f
            binding.clAccountSecret.interruptTouch = false
            binding.tvLogin.alpha = 1f
            binding.tvLogin.isClickable = true
        }else{
            binding.clAccountSecret.alpha = 0.5f
            binding.clAccountSecret.interruptTouch = true
            binding.tvLogin.alpha = 0.5f
            binding.tvLogin.isClickable = false
            binding.edtPhone.clearFocus()
            binding.edtCode.clearFocus()
        }
    }


    val keyListener1:View.OnClickListener = OnClickListener {
        WebViewActivity.startActivity(
            this,
            getString(R.string.login_agree_title_user_protocol),
            getString(R.string.url_user_protocol)
        )
    }

    val keyListener2:View.OnClickListener = OnClickListener {
        WebViewActivity.startActivity(
            this,
            getString(R.string.login_agree_title_privacy),
            getString(R.string.url_privacy)
        )

    }


    private fun regToWx() {
        wxVm.regToWx()
    }

    private fun onWxClick(){
        if(!wxVm.isWxInstalled()){
            Toast.makeText(this,getString(R.string.login_wx_null),Toast.LENGTH_SHORT).show()
            return
        }

        wxVm.login()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val result = intent.getBooleanExtra("result", false)
        if(!result){
            if(wxVm.loggingIn)
                Toast.makeText(this,getString(R.string.login_wx_fail),Toast.LENGTH_SHORT).show()
            return
        }
        wxVm.accessTokenGet(intent)

    }


    private val bindingSexLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val intent = Intent()
        intent.putExtra(EXTRA_LOGIN_RESULT,true)
        setResult(RESULT_OK,intent)
        finish()
    }

    companion object{
        const val EXTRA_LOGIN_RESULT= "EXTRA_LOGIN_RESULT"
    }
}