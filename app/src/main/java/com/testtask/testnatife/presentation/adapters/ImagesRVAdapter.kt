package com.testtask.testnatife.presentation.adapters

import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.testtask.testnatife.R
import com.testtask.testnatife.databinding.ImageItemBinding
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel

class ImagesRVAdapter:
    BaseQuickAdapter<ImageRVModel, ImagesRVAdapter.ImagesVewHolder>(R.layout.image_item), LoadMoreModule {

    inner class ImagesVewHolder(view: View): BaseViewHolder(view) {
        private val binding = ImageItemBinding.bind(view)

        fun bind(image: ImageRVModel) = with(binding){
            Glide.with(binding.ivImageItem)
                .load(image.imageUrl)
                .centerCrop()
                .into(ivImageItem)

            checkImageIsSelected(image)

            ivImageItem.setOnLongClickListener {
                if (!imbDeleteImage.isVisible) {
                    setItemStatus(isSelected = true, image)
                }
                else {
                    setItemStatus(isSelected = false, image)
                }
                return@setOnLongClickListener true
            }
        }

        private fun ImageItemBinding.setItemStatus(isSelected: Boolean ,image: ImageRVModel) {
            imbDeleteImage.visibility = if (isSelected) View.VISIBLE else View.GONE
            image.isSelected = isSelected
        }

        private fun ImageItemBinding.checkImageIsSelected(image: ImageRVModel) {
            if (image.isSelected) {
                imbDeleteImage.visibility = View.VISIBLE
            } else {
                imbDeleteImage.visibility = View.GONE
            }
        }
    }

    override fun convert(holder: ImagesVewHolder, item: ImageRVModel) {
        holder.bind(item)
    }
}