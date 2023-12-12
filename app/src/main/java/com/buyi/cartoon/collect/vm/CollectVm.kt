package com.buyi.cartoon.collect.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.db.bean.CollectBean
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.main.eventbus.MsgEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CollectVm(application: Application) : AndroidViewModel(application) {

    private val TAG = CollectVm::class.java.simpleName

    var offset = 0
    var collectLoadResultLd = MutableLiveData<Boolean>()
    var noMoreDataLd = MutableLiveData<Boolean>()
    var collectList = ArrayList<CollectBean>()

    init {
        Log.i(TAG,"init")
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        Log.i(TAG,"onCleared")
        EventBus.getDefault().unregister(this)
        super.onCleared()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MsgEvent) {
        Log.e(TAG,"onMessageEvent: $event")
        if(event.msgType == MsgEvent.COLLECT_ADD) {
//            val collect = event.msgObject as CollectBean
//            offset +=1
//            collectList.add(0,collect)
//            collectLoadResultLd.postValue(true)
            offset = 0
            collectList.clear()
        }else if(event.msgType == MsgEvent.COLLECT_REMOVE){
            val id = event.msgObject as Int
            val iterator = collectList.iterator()
            while (iterator.hasNext()){
                val next = iterator.next()
                if(next.id == id){
                    offset -=1
                    iterator.remove()
                    collectLoadResultLd.postValue(true)
                    break
                }
            }
        }else if(event.msgType == MsgEvent.COLLECT_UPDATE){
            val cartoonId = event.msgObject as Int
            val collectById = DbManager.getCollectById(-1, cartoonId.toLong())
            if(collectById != null){
                collectById.lastReadingChapter?.let { updateCollectReading(cartoonId, it) }
            }
        }

    }

    fun updateCollectReading(cartoonId:Int,readingChapter:Int):Int?{
        var i = 0
        for (item in collectList){
            if(item.id == cartoonId){
                item.lastReadingChapter = readingChapter
                return i++
            }
        }
        return null
    }

    fun fetchCollect(){
        val single = DbManager.loadAllCollect(offset, 10, -1)

        single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Consumer<List<CollectBean>>{
                override fun accept(t: List<CollectBean>) {
                    offset += t.size
                    collectList.addAll(t)
                    if(t.isEmpty()){
                        noMoreDataLd.postValue(true)
                    }
                    collectLoadResultLd.postValue(true)
                }
            },object:Consumer<Throwable>{
                override fun accept(t: Throwable) {
                    collectLoadResultLd.postValue(false)
                }
            })

    }

    fun refresh(){
        offset = 0
        collectList.clear()
        fetchCollect()
    }

}