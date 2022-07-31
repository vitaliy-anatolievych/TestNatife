package com.testtask.testnatife.data.local.storages.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.testtask.testnatife.data.entity.ImageEntity

@Dao
interface ImagesStorageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImageToBlackList(imageEntity: ImageEntity)
}