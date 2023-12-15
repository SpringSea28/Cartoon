package com.buyi.cartoon.detail.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.account.ui.LoginActivity
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivityCommentDetailBinding
import com.buyi.cartoon.detail.ui.adapter.CommentDetailItemAdapter
import com.buyi.cartoon.detail.vm.CommentDetailVM
import com.buyi.cartoon.main.base.BaseActivity
import com.example.pagingdatademo.mvvm.ui.adapter.LoadStateFooterAdapter

class CommentDetailActivity : BaseActivity<ActivityCommentDetailBinding>() {

    override val TAG: String
        get() = CommentDetailActivity::class.simpleName!!

    private val commentDetailVM: CommentDetailVM by viewModels()
    private var writeCommentFlag = false


    private val contentAdapter = CommentDetailItemAdapter()
    private val footerAdapter = LoadStateFooterAdapter {
        contentAdapter.retry()
    }

    override fun getBindingView(): ActivityCommentDetailBinding {
        return ActivityCommentDetailBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        getIntentData()
        initUi()
        initVm()
        getData()
    }

    private fun getIntentData(){
    }

    private fun initUi(){
        setPadding(true)
        onBackPressedDispatcher.addCallback(this,onBackPress)
        binding.title.tvTile.text = getString(R.string.comment_detail_title)
        binding.title.imgBack.setOnClickListener { onBackPress.handleOnBackPressed() }

        binding.tvPublish.setOnClickListener { goWriteComment() }
        binding.edtComment.setOnClickListener { goWriteComment() }



        binding.rcvComment.layoutManager= LinearLayoutManager(this)
        binding.rcvComment.adapter = contentAdapter.withLoadStateFooter(footerAdapter)

        binding.refreshLayout.setOnRefreshListener{
            contentAdapter.refresh()
        }

        contentAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.Loading -> {
                    Log.d(TAG,"refresh: loading")
                }
                is LoadState.NotLoading -> {
                    Log.d(TAG,"refresh: NotLoading")
                    binding.refreshLayout.finishRefresh()
                }
                is LoadState.Error -> {Log.d(TAG,"refresh: Error")
                    binding.refreshLayout.finishRefresh(false)
                }
            }
            when(it.append){
                is LoadState.Loading -> {Log.d(TAG,"append: loading")}
                is LoadState.NotLoading -> { Log.d(TAG, "append: NotLoading") }
                is LoadState.Error -> {Log.d(TAG,"append: Error")
                }
            }
        }
    }


    private fun initVm(){

    }

    private fun getData(){

    }


    private fun goWriteComment(){
        if(UserManager.getToken().isNullOrBlank()){
            loginLaunch.launch(Intent(this, LoginActivity::class.java))
        }else{
            writeCommentLaunch.launch(Intent(this,WriteCommentActivity::class.java))
        }
    }

    private val loginLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val login = it.data?.getBooleanExtra(LoginActivity.EXTRA_LOGIN_RESULT,false)
            if(login == true){
                goWriteComment()
            }
        }
    }

    private val writeCommentLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val result = it.data?.getBooleanExtra(WriteCommentActivity.EXTRA_WRITE_COMMENT,false)
            if(result == true){
                writeCommentFlag = true
                contentAdapter.refresh()
            }
        }
    }

    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (writeCommentFlag) {
                val intent = Intent()
                intent.putExtra(WriteCommentActivity.EXTRA_WRITE_COMMENT, true)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }

}