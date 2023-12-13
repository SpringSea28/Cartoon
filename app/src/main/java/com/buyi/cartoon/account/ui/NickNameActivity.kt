package com.buyi.cartoon.account.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.buyi.cartoon.R
import com.buyi.cartoon.account.bean.UserInfo
import com.buyi.cartoon.account.util.UserConstant
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.account.vm.EditNickNameVM
import com.buyi.cartoon.databinding.ActivityEditNickNameBinding
import com.buyi.cartoon.databinding.ActivityPersonInfoBinding
import com.buyi.cartoon.main.base.BaseActivity

class NickNameActivity : BaseActivity<ActivityEditNickNameBinding>() {

    override val TAG: String
        get() = NickNameActivity::class.simpleName!!

    private val editNickNameVM: EditNickNameVM by viewModels()


    override fun getBindingView(): ActivityEditNickNameBinding {
        return ActivityEditNickNameBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.imgBack.setOnClickListener { finish() }
        binding.title.tvTile.text = getString(R.string.person_nick_name)
        binding.title.tvRight.text = getString(R.string.confirm)
        binding.edtComment.requestFocus()

        binding.title.tvRight.setOnClickListener {
            val comment = binding.edtComment.text.toString()
            if(comment.isNullOrBlank()){
                return@setOnClickListener
            }
            editNickNameVM.publish(comment)
        }
    }

    private fun initVm(){
        editNickNameVM.publishLd.observe(this){
            if(it){
                Toast.makeText(this,getString(R.string.person_nick_name_publish_suc),
                    Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.putExtra(UserConstant.EXTRA_NICK_NAME,editNickNameVM.nickName)
                setResult(RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this,getString(R.string.person_nick_name_publish_fail),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }



}