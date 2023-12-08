package com.buyi.cartoon.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CollectBean::class],version = 1)
abstract class CartoonDb : RoomDatabase(){
  abstract fun collectDao() : CollectDao
}