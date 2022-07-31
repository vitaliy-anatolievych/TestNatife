package com.testtask.testnatife.data.local.storages.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testtask.testnatife.data.entity.ImageEntity

@Dao
interface ImagesStorageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImageToBlackList(imageEntity: ImageEntity)

    @Query("SELECT * FROM black_list")
    fun getBlackList(): List<ImageEntity>
}