package com.buyi.cartoon.db.dao

import androidx.room.*
import com.buyi.cartoon.db.bean.LastReadingChapterBean
import io.reactivex.rxjava3.core.Completable

@Dao
interface LastReadingChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastReadingChapter(vararg lastReadingChapterBean: LastReadingChapterBean): Completable

    @Update
    fun updateLastReadingChapter(vararg lastReadingChapterBean: LastReadingChapterBean): Completable

    @Delete
    fun deleteLastReadingChapter(vararg lastReadingChapterBean: LastReadingChapterBean): Completable

    @Query("select * from last_reading_chapter where id_server ==:id")
    fun getLastReadingChapterById(id: Long): LastReadingChapterBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastReadingChapterSync(lastReadingChapterBean: LastReadingChapterBean): Long

    @Update
    fun updateLastReadingChapterSync(lastReadingChapterBean: LastReadingChapterBean): Int

    @Delete
    fun deleteLastReadingChapterSync(lastReadingChapterBean: LastReadingChapterBean): Int

    @Query("delete from last_reading_chapter where id_server ==:id")
    fun deleteLastReadingChapterSync(id: Long): Int
}