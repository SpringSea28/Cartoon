package com.buyi.cartoon.detail.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.databinding.ActivityWriteCommentBinding
import com.buyi.cartoon.detail.vm.WriteCommentVM
import com.buyi.cartoon.main.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class WriteCommentActivity : BaseActivity<ActivityWriteCommentBinding>() {

    override val TAG: String
        get() = WriteCommentActivity::class.simpleName!!

    private val writeCommentVM:WriteCommentVM by viewModels()

    override fun getBindingView(): ActivityWriteCommentBinding {
        return ActivityWriteCommentBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
       initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.imgBack.setOnClickListener { finish() }
        binding.title.tvTile.text = getString(R.string.comment_write_title)
        binding.title.tvRight.text = getString(R.string.comment_write_publish)
        binding.edtComment.requestFocus()

        binding.title.tvRight.setOnClickListener {
            val comment = binding.edtComment.text.toString()
            if(comment.isNullOrEmpty()){
                return@setOnClickListener
            }
            writeCommentVM.publish(comment)
        }
    }

    private fun initVm(){
        writeCommentVM.publishLd.observe(this){
            if(it){
                Toast.makeText(this,getString(R.string.comment_write_publish_suc),
                Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,getString(R.string.comment_write_publish_fail),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}