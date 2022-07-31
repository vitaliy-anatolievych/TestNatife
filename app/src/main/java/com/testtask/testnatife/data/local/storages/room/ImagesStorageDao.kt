package com.testtask.testnatife.data.local.storages.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testtask.testnatife.data.entity.ImageBlockEntity
import com.testtask.testnatife.data.entity.ImageEntity

@Dao
interface ImagesStorageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImageToBlackList(imageBlockEntity: ImageBlockEntity)

    @Query("DELETE FROM image_cache WHERE id=:id")
    fun deleteImageFromCache(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImageToCache(imageEntity: List<ImageEntity>)

    @Query("SELECT * FROM image_cache WHERE `query`=:query")
    fun getImagesFromCache(query: String): List<ImageEntity>

    @Query("SELECT * FROM black_list")
    fun getBlackList(): List<ImageBlockEntity>
}