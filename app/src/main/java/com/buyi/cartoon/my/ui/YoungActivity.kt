package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.account.ui.LoginActivity
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivityYoungBinding
import com.buyi.cartoon.detail.ui.adapter.CommentDetailItemAdapter
import com.buyi.cartoon.detail.vm.CommentDetailVM
import com.buyi.cartoon.main.MainActivity
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.my.ui.adapter.YoungItemAdapter
import com.example.pagingdatademo.mvvm.ui.adapter.LoadStateFooterAdapter

class YoungActivity : BaseActivity<ActivityYoungBinding>() {

    override val TAG: String
        get() = YoungActivity::class.simpleName!!



    private val contentAdapter = YoungItemAdapter()
    private val footerAdapter = LoadStateFooterAdapter {
        contentAdapter.retry()
    }

    override fun getBindingView(): ActivityYoungBinding {
        return ActivityYoungBinding.inflate(layoutInflater)
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

        binding.tvExit.setOnClickListener { exitYoung() }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        getDrawable(R.drawable.divider_8_fff4f5f9)?.let { dividerItemDecoration.setDrawable(it) }
        binding.rcvCartoon.layoutManager= LinearLayoutManager(this)
        binding.rcvCartoon.adapter = contentAdapter.withLoadStateFooter(footerAdapter)
        binding.rcvCartoon.addItemDecoration(dividerItemDecoration)

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


    private fun exitYoung(){
        exitLaunch.launch(Intent(this,ExitYoungSecretActivity::class.java))
    }

    private fun goMain(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    private val exitLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val exitResult = it.data?.getBooleanExtra(ExitYoungSecretActivity.EXTRA_EXIT_YOUNG_RESULT,false)
            if(exitResult == true){
                finish()
                goMain()
            }
        }
    }


    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {


        }
    }

}