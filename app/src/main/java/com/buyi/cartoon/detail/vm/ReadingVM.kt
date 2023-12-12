package com.buyi.cartoon.detail.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.db.bean.BrownBean
import com.buyi.cartoon.db.bean.LastReadingChapterBean
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.http.bean.DemoReqData
import com.buyi.cartoon.http.datasource.HomeClassifyDs
import com.buyi.cartoon.main.eventbus.MsgEvent
import kotlinx.coroutines.flow.Flow
import org.greenrobot.eventbus.EventBus
import java.util.Calendar


class ReadingVM(application: Application) : AndroidViewModel(application) {

    private val TAG :String = ReadingVM::class.java.simpleName

    val classListLD = MutableLiveData<List<ClassifyInfoBean>>()

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20,3)
    ) {
        HomeClassifyDs()
    }.flow
        .cachedIn(viewModelScope)

    fun fetchCartoon(startPos:Int): Flow<PagingData<DemoReqData.DataBean.DatasBean>> {
        var flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20,3)
        ) {
            HomeClassifyDs(startPos)
        }.flow
        return flow
    }

    fun updateCollectReadingChapter(cartoonId:Int,chapter:Int){
        val collectById = DbManager.getCollectById(-1, cartoonId.toLong())
        if(collectById != null){
            collectById.lastReadingChapter = chapter
            DbManager.updatCollectSync(collectById)
            val msgEvent = MsgEvent()
            msgEvent.msgType = MsgEvent.COLLECT_UPDATE
            msgEvent.msgObject = collectById.id
            EventBus.getDefault().post(msgEvent)
        }
    }

    fun updateLastReadingChapter(cartoonId:Int,chapter:Int){
        val chapterBean = DbManager.getLastReadingChapterById(-1, cartoonId.toLong())
        if(chapterBean == null) {
            val lastReadingChapterBean = LastReadingChapterBean()
            lastReadingChapterBean.id = cartoonId
            lastReadingChapterBean.lastReadingChapter = chapter
            lastReadingChapterBean.userId = -1
            val insertLastReadingChapter =
                DbManager.insertLastReadingChapter(lastReadingChapterBean)
            Log.e(TAG, "insert chapter $insertLastReadingChapter")
        }else{
            chapterBean.lastReadingChapter = chapter
            val updateLastReadingChapterSync = DbManager.updateLastReadingChapterSync(chapterBean)
            Log.e(TAG, "update chapter $updateLastReadingChapterSync")
        }
    }


    fun updateBrown(simpleInfoBean: CartoonSimpleInfoBean?,chapter:Int){
        if (simpleInfoBean == null){
            Log.e(TAG, "updateBrown info null")
            return
        }
        val brownBeanList = DbManager.loadAllBrown(-1)
        var itemExist: BrownBean? = null
        for (item in brownBeanList){
            if(item.id == simpleInfoBean?.id){
                itemExist = item
                break
            }
        }
        if(itemExist != null){
            itemExist.updateTime = Calendar.getInstance().timeInMillis
            itemExist.lastReadingChapter  =chapter
            DbManager.updateBrown(itemExist!!)
        }else{
            val brownBean = BrownBean()
            brownBean.id = simpleInfoBean?.id
            brownBean.name = simpleInfoBean?.title
            brownBean.imgUrl = simpleInfoBean?.imgUrl
            brownBean.lastReadingChapter = chapter
            brownBean.lastReadingChapterTitle = "woyebuzhidao"
            brownBean.lastUpdateChapter = 16
            brownBean.status = 1
            brownBean.userId = -1
            brownBean.updateTime = Calendar.getInstance().timeInMillis
            DbManager.insertBrown(brownBean)
        }
        for(i in 20 until brownBeanList.size){
            DbManager.delBrown(brownBeanList[i])
        }
    }

    fun updateBrown(cartoonId:Int?,chapter:Int){
        if (cartoonId == null){
            Log.e(TAG, "updateBrown id null")
            return
        }
        val brownBean = DbManager.getBrownById(-1, cartoonId.toLong())
        if(brownBean != null){
            brownBean.updateTime = Calendar.getInstance().timeInMillis
            brownBean.lastReadingChapter  =chapter
            DbManager.updateBrown(brownBean)
        }
    }

}