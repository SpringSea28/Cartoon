package com.buyi.cartoon.detail.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserManager
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
        Log.e(TAG,"initUi showMenu : $showMenu")
        onBackPressedDispatcher.addCallback(this,onBackPress)
        showMenu()
        showMask()
        binding.title.imgBack.setImageResource(R.mipmap.go_back_white)
        binding.title.imgBack.setOnClickListener{onBackPress.handleOnBackPressed()}
        binding.title.tvTile.text = getString(R.string.reading_chapter,chapter)
        binding.title.tvTile.setTextColor(getColor(R.color.white))
        binding.title.imgRight.setImageResource(R.mipmap.more_white)


        binding.clBottom.tvComment.setOnClickListener { goComment() }
        binding.clBottom.tvPublish.setOnClickListener { goComment() }
        binding.clBottom.imgDirectory.setOnClickListener { goDirectory() }
        binding.clBottom.tvPrevious.setOnClickListener { previousChapter() }
        binding.clBottom.tvNext.setOnClickListener { nextChapter() }
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
            Log.e(TAG,"onItemClickListener showMenu : $showMenu")
            showMenu()
        }

        binding.rcvCartoon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.e(TAG,"rcvCartoon onScrollStateChanged $newState")
                if(newState == RecyclerView.SCROLL_STATE_SETTLING){
                    if(showMenu){
                        Log.e(TAG,"onScrollStateChanged showMenu : $showMenu")
                        showMenu = !showMenu
                        showMenu()
                    }
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

        })
    }

    private fun showMenu(){
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
            binding.clBottom.imgLight.isSelected = true
        }else{
            binding.vMask.setBackgroundColor(Color.TRANSPARENT)
            binding.clBottom.imgLight.isSelected = false
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

    private fun previousChapter(){
        if(chapter < 1){
            Toast.makeText(this,getString(R.string.reading_no_more_previous_one),
                Toast.LENGTH_SHORT).show()
            return
        }
        chapter -= 1
        binding.title.tvTile.text = getString(R.string.reading_chapter,chapter)
        fetchCartoon(chapter)
    }

    private fun nextChapter(){
        if(chapter == 5){
            Toast.makeText(this,getString(R.string.reading_no_more_next_one),
                Toast.LENGTH_SHORT).show()
            return
        }
        chapter += 1
        binding.title.tvTile.text = getString(R.string.reading_chapter,chapter)
        fetchCartoon(chapter)
    }

    private fun goComment(){
//        if(UserManager.getUserId() == null){
//            Toast.makeText(this,getString(R.string.login_first),
//                Toast.LENGTH_SHORT).show()
//
//            return
//        }

        val intent = Intent(this,WriteCommentActivity::class.java)
        startActivity(intent)
    }

    private fun goDirectory(){
        val intent = Intent(this,ChapterDirectoryActivity::class.java)
        intent.putExtra(ConstantApp.INTENT_CHAPTER,chapter)
        directoryLaunch.launch(intent)
    }

    private val directoryLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val chapter = it.data?.getIntExtra(ConstantApp.INTENT_CHAPTER,-1)
            if(chapter!= null && chapter > 0){
                this.chapter = chapter
                binding.title.tvTile.text = getString(R.string.reading_chapter,chapter)
                fetchCartoon(chapter)
            }
        }
    }

    private val onBackPress = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val intent = Intent()
            intent.putExtra(ConstantApp.INTENT_CHAPTER,chapter)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}