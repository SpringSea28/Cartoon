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
import com.buyi.cartoon.db.LastReadingChapterBean
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.http.bean.DemoReqData
import com.buyi.cartoon.http.datasource.HomeClassifyDs
import com.buyi.cartoon.main.eventbus.MsgEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit


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


}