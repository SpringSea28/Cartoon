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

    @Query("select * from brown where user_id =:userId ORDER BY update_time DESC LIMIT :limit OFFSET :offset ")
    fun loadAllBrown(offset:Int,limit:Int,userId: Long): List<BrownBean>

    @Query("select * from brown where user_id =:userId ORDER BY update_time ASC")
    fun loadAllBrown(userId: Long): List<BrownBean>


    @Query("select * from brown where user_id =:userId and id_server ==:id")
    fun getBrownById(userId: Long,id: Long): BrownBean?
    
}