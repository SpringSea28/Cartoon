package com.buyi.cartoon.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.buyi.cartoon.db.bean.CollectBean
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

    @Query("select * from collect ORDER BY rowid DESC LIMIT :limit OFFSET :offset ")
    fun loadAllCollect(offset:Int,limit:Int): Single<List<CollectBean>>

    @Query("select * from collect  LIMIT :limit OFFSET :offset ")
    fun loadAllCollectSync(offset:Int,limit:Int): List<CollectBean>

    @Query("select * from collect ")
    fun loadAllCollectPs(): PagingSource<Int, CollectBean>

    @Query("select * from collect where rowid ==:rowId")
    fun getCollect(rowId: Long): CollectBean?

    @Query("select * from collect where id_server ==:id")
    fun getCollectById(id: Long): CollectBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectSync(collect: CollectBean): Long

    @Update
    fun updateCollectSync(collect: CollectBean): Int

    @Delete
    fun deleteCollectSync(collect: CollectBean): Int

    @Query("delete from collect where id_server ==:id")
    fun deleteCollectSync(id: Long): Int
}