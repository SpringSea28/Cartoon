package com.buyi.cartoon.db.dao

import androidx.room.*
import com.buyi.cartoon.db.bean.BrownBean
import com.buyi.cartoon.db.bean.CollectBean

@Dao
interface BrownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBrown(vararg brownBean: BrownBean): LongArray?

    @Update
    fun updateBrown(vararg brownBean: BrownBean): Int

    @Delete
    fun deleteBrown(vararg brownBean: BrownBean): Int

    @Query("select * from brown ORDER BY update_time DESC LIMIT :limit OFFSET :offset ")
    fun loadAllBrown(offset:Int,limit:Int): List<BrownBean>

    @Query("select * from brown ORDER BY update_time ASC")
    fun loadAllBrown(): List<BrownBean>


    @Query("select * from brown where id_server ==:id")
    fun getBrownById(id: Long): BrownBean?
    
}