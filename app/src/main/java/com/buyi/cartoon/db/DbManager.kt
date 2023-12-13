package com.buyi.cartoon.db

import androidx.paging.PagingSource
import androidx.room.Delete
import androidx.room.Room
import com.buyi.cartoon.db.bean.BrownBean
import com.buyi.cartoon.db.bean.CollectBean
import com.buyi.cartoon.db.bean.LastReadingChapterBean
import com.buyi.cartoon.main.CartoonApp
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

object DbManager {

    private val db : CartoonDb = Room.databaseBuilder(
        CartoonApp.instance(),
        CartoonDb::class.java, "CartoonDb"
    ).allowMainThreadQueries().build()


    fun insertCollect(vararg collectBean: CollectBean) : Completable {
        return db.collectDao().insertCollect(*collectBean)
    }

    fun insertCollectSync(collectBean: CollectBean) : Long {
        return db.collectDao().insertCollectSync(collectBean)
    }

    fun delCollect(vararg collectBean: CollectBean) : Completable {
        return db.collectDao().deleteCollect(*collectBean)
    }

    fun delCollectSync(collectBean: CollectBean) : Int {
        return db.collectDao().deleteCollectSync(collectBean)
    }

    fun delCollectSync(id: Long) : Int {
        return db.collectDao().deleteCollectSync(id)
    }

    fun updatCollectSync(collectBean: CollectBean) : Int {
        return db.collectDao().updateCollectSync(collectBean)
    }

    fun loadAllCollect(offset:Int,limit:Int): Single<List<CollectBean>> {
        return db.collectDao().loadAllCollect(offset,limit)
    }

    fun loadAllCollectSync(offset:Int,limit:Int):List<CollectBean>{
        return db.collectDao().loadAllCollectSync(offset,limit)
    }

    fun loadAllCollectPs(): PagingSource<Int, CollectBean> {
        return db.collectDao().loadAllCollectPs()
    }

    fun getCollect(rowId: Long): CollectBean?{
        return db.collectDao().getCollect(rowId)
    }

    fun getCollectById(id: Long): CollectBean?{
        return db.collectDao().getCollectById(id)
    }

    fun insertLastReadingChapter(lastReadingChapterBean: LastReadingChapterBean):Long{
        return db.lastReadingChapterDao().insertLastReadingChapterSync(lastReadingChapterBean)
    }

    fun getLastReadingChapterById(id: Long): LastReadingChapterBean?{
        return db.lastReadingChapterDao().getLastReadingChapterById(id)
    }

    fun updateLastReadingChapterSync(lastReadingChapterBean: LastReadingChapterBean): Int{
        return db.lastReadingChapterDao().updateLastReadingChapterSync(lastReadingChapterBean)
    }


    fun insertBrown(vararg brownBean: BrownBean) : LongArray? {
        return db.brownDao().insertBrown(*brownBean)
    }

    fun updateBrown(vararg brownBean: BrownBean) : Int {
        return db.brownDao().updateBrown(*brownBean)
    }

    fun delBrown(vararg brownBean: BrownBean) : Int {
        return db.brownDao().deleteBrown(*brownBean)
    }

    fun loadAllBrown(offset:Int,limit:Int): List<BrownBean> {
        return db.brownDao().loadAllBrown(offset,limit)
    }

    fun loadAllBrown(): List<BrownBean> {
        return db.brownDao().loadAllBrown()
    }

    fun getBrownById(id: Long): BrownBean?{
        return db.brownDao().getBrownById(id)
    }
}