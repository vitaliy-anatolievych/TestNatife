package com.testtask.testnatife.presentation.adapters.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageRVModel(
    val id: String,
    val imageUrl: String,
    var isSelected: Boolean
): Parcelable
