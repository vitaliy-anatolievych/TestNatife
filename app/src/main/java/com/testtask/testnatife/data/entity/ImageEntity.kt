package com.testtask.testnatife.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "black_list")
data class ImageEntity (
    @PrimaryKey
    val id: String,
    val imageUrl: String?
    )