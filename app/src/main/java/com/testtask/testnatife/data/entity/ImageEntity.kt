package com.testtask.testnatife.data.entity

import androidx.room.Entity

@Entity(tableName = "black_list")
data class ImageEntity (
    val id: String?,
    val imageUrl: String?
    )