package com.testtask.testnatife.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_cache")
data class ImageEntity (
    @PrimaryKey
    val id: String,
    val query: String,
    val imageUrl: String?
    )