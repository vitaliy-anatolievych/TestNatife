package com.testtask.testnatife.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.testtask.testnatife.domain.models.ImageModel

class ImageListDiffCallBack: DiffUtil.ItemCallback<ImageModel>() {

    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem == newItem
    }
}