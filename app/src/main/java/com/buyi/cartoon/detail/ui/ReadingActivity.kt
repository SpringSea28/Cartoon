package com.buyi.cartoon.detail.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityCartoonReadingBinding
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.detail.ui.adapter.ReadingCartoonAdapter
import com.buyi.cartoon.detail.vm.ReadingVM
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReadingActivity : BaseActivity<ActivityCartoonReadingBinding>() {

    override val TAG: String
        get() = ReadingActivity::class.simpleName!!

    private var chapter:Int = 1
    private var showMenu = true
    private var nightMode = false

    private val readingVM:ReadingVM by viewModels()
    private val readingAdapter = ReadingCartoonAdapter()

    override fun getBindingView(): ActivityCartoonReadingBinding {
        return ActivityCartoonReadingBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        setPadding(true)
        getIntentData()
        initUi()
        fetchCartoon(chapter)
    }

    private fun getIntentData(){
        chapter = intent.getIntExtra(ConstantApp.INTENT_CHAPTER,1)
    }

    private fun initUi(){
        showMenu()
        showMask()
        binding.title.imgBack.setImageResource(R.mipmap.go_back_white)
        binding.title.imgBack.setOnClickListener{finish()}
        binding.title.tvTile.text = getString(R.string.reading_chapter,chapter)
        binding.title.tvTile.setTextColor(getColor(R.color.white))
        binding.title.imgRight.setImageResource(R.mipmap.more_white)


        binding.clBottom.tvComment.setOnClickListener {

        }
        binding.clBottom.tvPublish.setOnClickListener {  }
        binding.clBottom.imgDirectory.setOnClickListener {  }
        binding.clBottom.tvPrevious.setOnClickListener {  }
        binding.clBottom.tvNext.setOnClickListener {  }
        binding.clBottom.imgLight.setOnClickListener {
            nightMode = !nightMode
            showMask()
        }
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rcvCartoon.layoutManager = linearLayoutManager
        binding.rcvCartoon.setHasFixedSize(true)
        binding.rcvCartoon.adapter = readingAdapter
        readingAdapter.onItemClickListener = {
            showMenu = !showMenu
            showMenu()
        }

        binding.rcvCartoon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.e(TAG,"rcvCartoon onScrollStateChanged $newState")
                if(newState == RecyclerView.SCROLL_STATE_SETTLING){
                    if(showMenu){
                        showMenu = !showMenu
                        showMenu()
                    }
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

        })
    }

    private fun showMenu(){
        Log.e(TAG,"showMenu : $showMenu")
        if(showMenu){
            binding.title.root.visibility = View.VISIBLE
            binding.clBottom.root.visibility = View.VISIBLE
        }else{
            binding.title.root.visibility = View.GONE
            binding.clBottom.root.visibility = View.GONE
        }
    }

    private fun showMask(){
        Log.e(TAG,"showMask : $nightMode")
        if(nightMode){
            binding.vMask.setBackgroundColor(getColor(R.color.reading_mask))
        }else{
            binding.vMask.setBackgroundColor(Color.TRANSPARENT)
        }
    }


    private var dataJob: Job? = null

    private fun fetchCartoon(chapter:Int){
        dataJob?.cancel()
        dataJob = lifecycleScope.launch {
            readingVM.fetchCartoon(chapter).collectLatest {
                readingAdapter.submitData(it)
            }
        }
    }
}