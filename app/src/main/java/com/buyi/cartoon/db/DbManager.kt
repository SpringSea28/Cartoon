package com.buyi.cartoon.db

import androidx.room.Room
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

    fun updatCollectSync(collectBean: CollectBean) : Int {
        return db.collectDao().updateCollectSync(collectBean)
    }

    fun loadAllCollect(userId: Long): Single<List<CollectBean>> {
        return db.collectDao().loadAllCollect(userId)
    }

    fun loadAllCollectSync(userId: Long):List<CollectBean>{
        return db.collectDao().loadAllCollectSync(userId)
    }

    fun getCollect(userId: Long,rowId: Long): CollectBean?{
        return db.collectDao().getCollect(userId,rowId)
    }


}