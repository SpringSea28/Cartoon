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

    fun loadAllCollect(offset:Int,limit:Int,userId: Long): Single<List<CollectBean>> {
        return db.collectDao().loadAllCollect(offset,limit,userId)
    }

    fun loadAllCollectSync(offset:Int,limit:Int,userId: Long):List<CollectBean>{
        return db.collectDao().loadAllCollectSync(offset,limit,userId)
    }

    fun loadAllCollectPs(userId: Long): PagingSource<Int, CollectBean> {
        return db.collectDao().loadAllCollectPs(userId)
    }

    fun getCollect(userId: Long,rowId: Long): CollectBean?{
        return db.collectDao().getCollect(userId,rowId)
    }

    fun getCollectById(userId: Long,id: Long): CollectBean?{
        return db.collectDao().getCollectById(userId,id)
    }

    fun insertLastReadingChapter(lastReadingChapterBean: LastReadingChapterBean):Long{
        return db.lastReadingChapterDao().insertLastReadingChapterSync(lastReadingChapterBean)
    }

    fun getLastReadingChapterById(userId: Long,id: Long): LastReadingChapterBean?{
        return db.lastReadingChapterDao().getLastReadingChapterById(userId,id)
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

    fun loadAllBrown(offset:Int,limit:Int,userId: Long): List<BrownBean> {
        return db.brownDao().loadAllBrown(offset,limit,userId)
    }

    fun loadAllBrown(userId: Long): List<BrownBean> {
        return db.brownDao().loadAllBrown(userId)
    }

    fun getBrownById(userId: Long,id: Long): BrownBean?{
        return db.brownDao().getBrownById(userId,id)
    }
}