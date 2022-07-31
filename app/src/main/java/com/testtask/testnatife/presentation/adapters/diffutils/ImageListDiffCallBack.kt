package com.testtask.testnatife.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel

class ImageListDiffCallBack: DiffUtil.ItemCallback<ImageRVModel>() {

    override fun areItemsTheSame(oldItem: ImageRVModel, newItem: ImageRVModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageRVModel, newItem: ImageRVModel): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}