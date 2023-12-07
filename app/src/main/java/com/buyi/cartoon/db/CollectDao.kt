package com.buyi.cartoon.db

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CollectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollect(vararg collectBean: CollectBean): Completable

    @Update
    fun updateCollect(vararg collectBean: CollectBean): Completable

    @Delete
    fun deleteCollect(vararg collectBean: CollectBean): Completable

    @Query("select * from collect where user_id =:userId")
    fun loadAllCollect(userId: Long): Single<List<CollectBean>>

    @Query("select * from collect where user_id =:userId")
    fun loadAllCollectSync(userId: Long): List<CollectBean>

    @Query("select * from collect where user_id =:userId and rowid ==:rowId")
    fun getCollect(userId: Long,rowId: Long): CollectBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectSync(collect: CollectBean): Long

    @Update
    fun updateCollectSync(collect: CollectBean): Int

    @Delete
    fun deleteCollectSync(collect: CollectBean): Int
}