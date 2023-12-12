package com.buyi.cartoon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.buyi.cartoon.db.bean.BrownBean
import com.buyi.cartoon.db.bean.CollectBean
import com.buyi.cartoon.db.bean.LastReadingChapterBean
import com.buyi.cartoon.db.dao.BrownDao
import com.buyi.cartoon.db.dao.CollectDao
import com.buyi.cartoon.db.dao.LastReadingChapterDao

@Database(entities = [CollectBean::class, LastReadingChapterBean::class,BrownBean::class],version = 1)
abstract class CartoonDb : RoomDatabase(){
  abstract fun collectDao() : CollectDao

  abstract fun lastReadingChapterDao() : LastReadingChapterDao

  abstract fun brownDao(): BrownDao
}