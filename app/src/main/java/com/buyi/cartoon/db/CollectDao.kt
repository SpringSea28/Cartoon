package com.buyi.cartoon.db

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface CollectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollect(vararg collectBean: CollectBean): Completable

    @Update
    fun updateCollect(vararg collectBean: CollectBean): Completable

    @Delete
    fun deleteCollect(vararg collectBean: CollectBean): Completable

    @Query("select * from collect where user_id =:userId ORDER BY rowid DESC LIMIT :limit OFFSET :offset ")
    fun loadAllCollect(offset:Int,limit:Int,userId: Long): Single<List<CollectBean>>

    @Query("select * from collect where user_id =:userId  LIMIT :limit OFFSET :offset ")
    fun loadAllCollectSync(offset:Int,limit:Int,userId: Long): List<CollectBean>

    @Query("select * from collect where user_id =:userId")
    fun loadAllCollectPs(userId: Long): PagingSource<Int,CollectBean>

    @Query("select * from collect where user_id =:userId and rowid ==:rowId")
    fun getCollect(userId: Long,rowId: Long): CollectBean?

    @Query("select * from collect where user_id =:userId and id_server ==:id")
    fun getCollectById(userId: Long,id: Long): CollectBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectSync(collect: CollectBean): Long

    @Update
    fun updateCollectSync(collect: CollectBean): Int

    @Delete
    fun deleteCollectSync(collect: CollectBean): Int

    @Query("delete from collect where id_server ==:id")
    fun deleteCollectSync(id: Long): Int
}