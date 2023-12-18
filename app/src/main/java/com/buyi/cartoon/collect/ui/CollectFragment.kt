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
import com.buyi.cartoon.db.DbManager
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
        Log.i(TAG, "onDestroy")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        EventBus.getDefault().register(this)
        return view
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MsgEvent) {
        Log.e(TAG,"onMessageEvent: $event")
        if(event.msgType == MsgEvent.COLLECT_ADD) {
            collectVm.refresh()
        }else if(event.msgType == MsgEvent.COLLECT_REMOVE){
            val id = event.msgObject as Int
            collectVm.removeItem(id)
        }else if(event.msgType == MsgEvent.COLLECT_UPDATE){
            if(event.msgObject != null ) {
                val array = event.msgObject as IntArray
                Log.e(TAG,"COLLECT_UPDATE: ${array[0]},${array[1]}")
                val pos = collectVm.updateCollectReading(array[0], array[1])
                if(pos!=null ) {
                    val updateContent = CollectUpdateBean<Int>()
                    updateContent.type = CollectUpdateBean.TYPE_READING_CHAPTER
                    updateContent.content = array[1]
                    contentAdapter.notifyItemChanged(pos,updateContent)
                }
            }
        }

    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("restore",true)
        super.onSaveInstanceState(outState)
        Log.e(TAG,"onSaveInstanceState")
    }

    private var restore:Boolean? = null
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        restore = savedInstanceState?.getBoolean("restore")
        Log.e(TAG,"onViewStateRestored")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG,"onHiddenChanged $hidden")
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
        binding.root.setOnClickListener {  }
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
//            val chapter = it.data?.getIntExtra(ConstantApp.INTENT_CHAPTER,-1)
//            val cartoonId = it.data?.getIntExtra(ConstantApp.INTENT_CARTOON_ID,-1)
//            Log.i(TAG,"return cartoonId $cartoonId")
//            Log.i(TAG,"return chapter $chapter")
//            if(cartoonId == null || cartoonId<0){
//                return@registerForActivityResult
//            }
//            if(chapter!= null && chapter > 0){
//                val pos = collectVm.updateCollectReading(cartoonId,chapter)
//                if(pos!=null ) {
//                    val updateContent = CollectUpdateBean<Int>()
//                    updateContent.type = CollectUpdateBean.TYPE_READING_CHAPTER
//                    updateContent.content = chapter
//                    contentAdapter.notifyItemChanged(pos,updateContent)
//                }
//            }
        }
    }

}