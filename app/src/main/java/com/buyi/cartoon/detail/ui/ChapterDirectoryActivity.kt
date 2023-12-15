package com.buyi.cartoon.detail.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityChapterDiretoryBinding
import com.buyi.cartoon.detail.ui.adapter.ChapterDirectoryAdapter
import com.buyi.cartoon.detail.vm.ChapterDirectoryVM
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp

class ChapterDirectoryActivity : BaseActivity<ActivityChapterDiretoryBinding>() {

    override val TAG: String
        get() = ChapterDirectoryActivity::class.simpleName!!

    private val directoryVM: ChapterDirectoryVM by viewModels()

    private val contentAdapter = ChapterDirectoryAdapter()

    private var sort = ConstantApp.SORT_UP
    private var chapter:Int = -1


    override fun getBindingView(): ActivityChapterDiretoryBinding {
        return ActivityChapterDiretoryBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        getIntentData()
        initUi()
        initVm()
        getData()
    }

    private fun getIntentData(){
        chapter = intent.getIntExtra(ConstantApp.INTENT_CHAPTER,1)
    }

    private fun initUi(){
        setPadding(false)
        binding.title.tvTile.text = getString(R.string.chapter_directory_title)
        binding.title.imgBack.setOnClickListener { finish() }

        binding.tvSort.setOnClickListener {
            sort()
        }

        binding.rcvCartoon.layoutManager= LinearLayoutManager(this)
        binding.rcvCartoon.adapter = contentAdapter
        contentAdapter.updateSort(sort)
        contentAdapter.updateReadingChapter(chapter)
        contentAdapter.onItemClickListener = { position, chapterInfo ->
            val intent = Intent()
            intent.putExtra(ConstantApp.INTENT_CHAPTER,chapterInfo.id)
            setResult(RESULT_OK,intent)
            finish()
        }

        binding.refreshLayout.setOnRefreshListener{

        }
    }


    private fun initVm(){
        directoryVM.chapterListLd.observe(this){
            contentAdapter.setData(it)
        }

        directoryVM.statusLd.observe(this){
            if(it == ConstantApp.CARTOON_STATUS_LOADING){
                binding.tvStatus.text = getString(R.string.detail_status_serialization)
            }else{
                binding.tvStatus.text = getString(R.string.detail_status_over)
            }
        }
        directoryVM.dateLd.observe(this){
            binding.tvDate.text = it
        }
        directoryVM.latestChapterLd.observe(this){
            binding.tvChapter.text = getString(R.string.chapter_more_serialization,it)
        }
    }

    private fun getData(){
        directoryVM.fetchChapter()
    }

    private fun sort(){
        if(sort == ConstantApp.SORT_UP){
            sort = ConstantApp.SORT_DOWN
            binding.imgSort.setImageResource(R.mipmap.up)
            binding.tvSort.text = getString(R.string.sort_up)
        }else{
            sort = ConstantApp.SORT_UP
            binding.imgSort.setImageResource(R.mipmap.down)
            binding.tvSort.text = getString(R.string.sort_down)
        }
        contentAdapter.updateSort(sort)
    }

}