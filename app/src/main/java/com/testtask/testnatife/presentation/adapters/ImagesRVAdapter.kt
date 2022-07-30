package com.testtask.testnatife.presentation.adapters

import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.testtask.testnatife.R
import com.testtask.testnatife.databinding.ImageItemBinding
import com.testtask.testnatife.domain.models.ImageModel

class ImagesRVAdapter:
    BaseQuickAdapter<ImageModel, ImagesRVAdapter.ImagesVewHolder>(R.layout.image_item), LoadMoreModule {

    inner class ImagesVewHolder(view: View): BaseViewHolder(view) {
        private val binding = ImageItemBinding.bind(view)

        fun bind(image: ImageModel) = with(binding){
            Glide.with(binding.ivImageItem)
                .load(image.imageUrl)
                .centerCrop()
                .into(ivImageItem)

            ivImageItem.setOnLongClickListener {
                if (!imbDeleteImage.isVisible) imbDeleteImage.visibility = View.VISIBLE
                else imbDeleteImage.visibility = View.GONE
                return@setOnLongClickListener true
            }
        }
    }

    override fun convert(holder: ImagesVewHolder, item: ImageModel) {
        holder.bind(item)
    }
}