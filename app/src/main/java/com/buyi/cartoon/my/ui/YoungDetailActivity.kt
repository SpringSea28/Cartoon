package com.buyi.cartoon.my.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityYoungDetailBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.my.ui.adapter.YoungDetailItemAdapter

class YoungDetailActivity : BaseActivity<ActivityYoungDetailBinding>() {

    override val TAG: String
        get() = YoungDetailActivity::class.simpleName!!

    private val contentAdapter = YoungDetailItemAdapter()


    override fun getBindingView(): ActivityYoungDetailBinding {
        return ActivityYoungDetailBinding.inflate(layoutInflater)
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
        binding.title.tvTile.text = getString(R.string.young_detail)
        binding.title.imgBack.setOnClickListener { finish() }

        binding.rcvContent.layoutManager= LinearLayoutManager(this)

    }


    private fun initVm(){

    }

    private fun getData(){

    }



    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            finish()
        }
    }

}