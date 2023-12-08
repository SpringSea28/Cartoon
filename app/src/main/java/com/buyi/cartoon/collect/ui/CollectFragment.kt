package com.buyi.cartoon.collect.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.collect.data.CollectUpdateBean
import com.buyi.cartoon.collect.ui.adapter.CollectAdapter
import com.buyi.cartoon.collect.vm.CollectVm
import com.buyi.cartoon.databinding.FragmentCollectBinding
import com.buyi.cartoon.db.CollectBean
import com.buyi.cartoon.detail.ui.ReadingActivity
import com.buyi.cartoon.main.base.BaseFragment
import com.buyi.cartoon.main.eventbus.MsgEvent
import com.buyi.cartoon.main.utils.ConstantApp
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CollectFragment : BaseFragment<FragmentCollectBinding>() {

    private val TAG = CollectFragment::class.simpleName!!
    private val collectVm : CollectVm by viewModels()
    private lateinit var contentAdapter :CollectAdapter


    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }


    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCollectBinding {
        return FragmentCollectBinding.inflate(inflater, container, false)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        Log.e(TAG,"initAllMembersData")
        initUi()
        initVm()
        getData()
    }

    private fun initUi(){
        binding.title.tvTile.text = getString(R.string.collect_title)

        contentAdapter = CollectAdapter()
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcvCartoon.layoutManager = linearLayoutManager
        binding.rcvCartoon.setHasFixedSize(true)
        binding.rcvCartoon.adapter = contentAdapter
        contentAdapter.onItemClickListener = {position, collectBean ->
            val intent = Intent(context, ReadingActivity::class.java)
            intent.putExtra(ConstantApp.INTENT_CHAPTER,collectBean.lastReadingChapter)
            intent.putExtra(ConstantApp.INTENT_CARTOON_ID,collectBean.id)
            readingLaunch.launch(intent)
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.resetNoMoreData()
            collectVm.refresh()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            collectVm.fetchCollect()
        }
    }

    private fun initVm(){
        collectVm.collectLoadResultLd.observe(this){
            binding.refreshLayout.finishRefresh()
            binding.refreshLayout.finishLoadMore(true)
            if(collectVm.collectList.isEmpty()){
                binding.layEmpty.root.visibility = View.VISIBLE
            }else{
                binding.layEmpty.root.visibility = View.GONE
            }

            val data = ArrayList(collectVm.collectList)
            contentAdapter.submitList(data)
        }

        collectVm.noMoreDataLd.observe(this){
            binding.refreshLayout.finishLoadMoreWithNoMoreData()
        }
    }

    private fun getData(){
        collectVm.refresh()
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
            if(chapter!= null && chapter > 0){
                val pos = collectVm.updateCollectReading(cartoonId,chapter)
                if(pos!=null ) {
                    val updateContent = CollectUpdateBean<Int>()
                    updateContent.type = CollectUpdateBean.TYPE_READING_CHAPTER
                    updateContent.content = chapter
                    contentAdapter.notifyItemChanged(pos,updateContent)
                }
            }
        }
    }

}