package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.collect.data.CollectUpdateBean
import com.buyi.cartoon.databinding.ActivityBrownBinding
import com.buyi.cartoon.detail.ui.ReadingActivity
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.my.ui.adapter.BrownAdapter
import com.buyi.cartoon.my.vm.BrownVm

class BrownActivity : BaseActivity<ActivityBrownBinding>() {

    override val TAG: String
        get() = BrownActivity::class.simpleName!!

    private lateinit var contentAdapter : BrownAdapter
    private val brownVm:BrownVm by viewModels()

    override fun getBindingView(): ActivityBrownBinding {
        return ActivityBrownBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
        getData()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.my_brown)

        contentAdapter = BrownAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rcvCartoon.layoutManager = linearLayoutManager
        binding.rcvCartoon.setHasFixedSize(true)
        binding.rcvCartoon.adapter = contentAdapter
        contentAdapter.onItemClickListener = {position, brownBean ->
            val intent = Intent(this, ReadingActivity::class.java)
            intent.putExtra(ConstantApp.INTENT_CHAPTER,brownBean.lastReadingChapter)
            intent.putExtra(ConstantApp.INTENT_CARTOON_ID,brownBean.id)
            readingLaunch.launch(intent)
        }

    }

    private fun initVm(){
        brownVm.brownListLd.observe(this){
            val data = ArrayList(it)
            contentAdapter.submitList(data)
        }
    }

    private fun getData(){
        brownVm.fetchBrown()
    }

    private val readingLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val chapter = it.data?.getIntExtra(ConstantApp.INTENT_CHAPTER,-1)
            val cartoonId = it.data?.getIntExtra(ConstantApp.INTENT_CARTOON_ID,-1)
            Log.i(TAG,"return cartoonId $cartoonId")
            Log.i(TAG,"return chapter $chapter")
            if(cartoonId == null || cartoonId<0){
                return@registerForActivityResult
            }
            brownVm.fetchBrown()

        }
    }
}