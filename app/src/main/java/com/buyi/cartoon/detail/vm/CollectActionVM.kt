package com.buyi.cartoon.detail.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.db.bean.CollectBean
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.main.eventbus.MsgEvent
import org.greenrobot.eventbus.EventBus


class CollectActionVM(application: Application) : AndroidViewModel(application) {

    private val TAG = CollectActionVM::class.java.simpleName


    val collectLd = MutableLiveData<Boolean>()
    val collectSucLd = MutableLiveData<Boolean>()
    val collectDelSucLd = MutableLiveData<Boolean>()

    val testUrl = arrayOf(
        "https://img1.baidu.com/it/u=225041176,855892897&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422",
        "https://img2.baidu.com/it/u=1665640482,1824915280&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img0.baidu.com/it/u=3145022701,3943645695&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889",
        "https://img2.baidu.com/it/u=710071810,2487788150&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800",
        "https://img2.baidu.com/it/u=2526519423,798155762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img2.baidu.com/it/u=3574827082,3267311681&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800",
        "https://img0.baidu.com/it/u=3279376560,3507705273&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img2.baidu.com/it/u=2242228201,4108939845&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"
        )


    fun fetchCollect(simpleInfoBean: CartoonSimpleInfoBean?){
        testCollect(simpleInfoBean?.id)
    }

    fun fetchCollect(cartoonId: Int?){
        testCollect(cartoonId)
    }


    fun setCollect(simpleInfoBean: CartoonSimpleInfoBean?,readingChapter:Int?){
        if(simpleInfoBean?.id == null){
            Log.e(TAG,"setCollect id null")
            return
        }
        if(collectLd.value == true){
            DbManager.delCollectSync(simpleInfoBean.id!!.toLong())
            collectLd.postValue(false)
            collectDelSucLd.postValue(true)
            val msgEvent = MsgEvent()
            msgEvent.msgType = MsgEvent.COLLECT_REMOVE
            msgEvent.msgObject = simpleInfoBean.id
            EventBus.getDefault().post(msgEvent)
        }else{
            val collectBean = CollectBean()
            collectBean.id = simpleInfoBean.id
            collectBean.name = simpleInfoBean.title
            collectBean.imgUrl = testUrl[0]
            collectBean.lastReadingChapter = readingChapter
            collectBean.lastReadingChapterTitle = "woyebuzhidao"
            collectBean.lastUpdateChapter = 16
            collectBean.status = 1
            collectBean.userId = -1
            DbManager.insertCollectSync(collectBean)
            collectLd.postValue(true)
            collectSucLd.postValue(true)
            val msgEvent = MsgEvent()
            msgEvent.msgType = MsgEvent.COLLECT_ADD
            msgEvent.msgObject = collectBean
            EventBus.getDefault().post(msgEvent)
        }
    }



    private fun testCollect(cartoonId: Int?){
        if(cartoonId == null){
            Log.e(TAG,"query collect id null")
            return
        }
        val collectById = DbManager.getCollectById(cartoonId.toLong())
        if(collectById != null)
            collectLd.postValue(true)
        else
            collectLd.postValue(false)
    }

}