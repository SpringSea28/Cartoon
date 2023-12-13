package com.buyi.cartoon.account.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.buyi.cartoon.R
import com.buyi.cartoon.account.ui.dialog.GenderBottomDialog
import com.buyi.cartoon.account.util.UserConstant
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.account.vm.PersonInfoVm
import com.buyi.cartoon.databinding.ActivityPersonInfoBinding
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.main.base.BaseActivity


class PersonInfoActivity : BaseActivity<ActivityPersonInfoBinding>() {

    override val TAG: String
        get() = PersonInfoActivity::class.simpleName!!

    private val personInfoVm:PersonInfoVm by viewModels()

    private var nickNameChange = false
    private var sexChange = false


    override fun getBindingView(): ActivityPersonInfoBinding {
        return ActivityPersonInfoBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback(this,onBackPress)
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.person_title)
        binding.title.imgBack.setOnClickListener { onBackPress.handleOnBackPressed() }

        val userInfo = UserManager.getUserInfo() ?: return
        binding.tvAccount.text = userInfo.phone
        updateHeader(userInfo.gender)
        updateSex(userInfo.gender)
        updateNickName(userInfo.nickName)
        updateBirthdate(userInfo.birthdate)

        binding.rlNickName.setOnClickListener { nickNameLaunch.launch(
            Intent(this, NickNameActivity::class.java)) }
        binding.rlSex.setOnClickListener { editGender() }
    }

    private fun initVm(){

    }

    private fun updateHeader(gender:Int?){
        if(gender == UserConstant.SEX_BOY){
            binding.imgHeader.setImageResource(R.mipmap.male)
        }else if(gender == UserConstant.SEX_GIRL){
            binding.imgHeader.setImageResource(R.mipmap.female)
        }else {
            binding.imgHeader.setImageResource(R.mipmap.user_iamge_70px)
        }
    }

    private fun updateSex(gender:Int?){
        if(gender == UserConstant.SEX_BOY){
            binding.tvSex.text = getString(R.string.sex_male)
        }else if(gender == UserConstant.SEX_GIRL){
            binding.tvSex.text = getString(R.string.sex_female)
        }else {
            binding.tvSex.text = getString(R.string.person_unset)
        }
    }

    private fun updateNickName(nickName:String?){
        if(nickName.isNullOrBlank()){
            binding.tvNickName.text = getString(R.string.person_unset)
            return
        }
        binding.tvNickName.text = nickName
    }

    private fun updateBirthdate(birthdate:String?){
        if(birthdate.isNullOrBlank()){
            binding.tvBirthdate.text = getString(R.string.person_unset)
            return
        }
        binding.tvBirthdate.text = birthdate
    }


    private fun editGender(){
        val dialog = GenderBottomDialog()
        val bundle = Bundle()
        dialog.arguments = bundle
        dialog.onGenderClick = {
            personInfoVm.bindSex(it)
            updateHeader(it)
            updateSex(it)
            nickNameChange = true
        }
        dialog.show(supportFragmentManager, "gender")
    }

    private val nickNameLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val nickName = it.data?.getStringExtra(UserConstant.EXTRA_NICK_NAME)
            if(!nickName.isNullOrBlank()){
                nickNameChange = true
                updateNickName(nickName)
            }
        }
    }


    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if(!nickNameChange && !sexChange){
                finish()
                return
            }
            val intent = Intent()
            if(nickNameChange){
                intent.putExtra(UserConstant.EXTRA_NICK_NAME,binding.tvNickName.text.toString())
            }
            if(sexChange){
                intent.putExtra(UserConstant.EXTRA_SEX,personInfoVm.sex)
            }

            setResult(RESULT_OK,intent)
            finish()
        }
    }
}