package com.testtask.testnatife.presentation.adapters

import android.os.Parcelable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.testtask.testnatife.R
import com.testtask.testnatife.databinding.ImageItemBinding
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class ImagesRVAdapter:
    BaseQuickAdapter<ImageRVModel, ImagesRVAdapter.ImagesVewHolder>(R.layout.image_item), LoadMoreModule, Parcelable {

    @IgnoredOnParcel
    private var deleteImage: ((ImageRVModel) -> Unit)? = null

    @IgnoredOnParcel
    private var onImageClicked: (() -> Unit)? = null

    inner class ImagesVewHolder(view: View): BaseViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.iv_image_item)
        private val deleteButton = view.findViewById<ImageButton>(R.id.imb_delete_image)

        fun bind(image: ImageRVModel) {

            Glide.with(imageView.context)
                .load(image.imageUrl)
                .centerCrop()
                .into(imageView)

            // For debug test
//            with(binding) {
//                tvIdItem.text = image.id
//                tvLinkItem.text = image.imageUrl
//            }

            checkImageIsSelected(image)

            imageView.setOnLongClickListener {
                if (!deleteButton.isVisible) {
                    setItemStatus(isSelected = true, image)
                }
                else {
                    setItemStatus(isSelected = false, image)
                }
                return@setOnLongClickListener true
            }

            imageView.setOnClickListener {
                onImageClicked?.invoke()
            }

            deleteButton.setOnClickListener {
                deleteItem(image)
            }
        }

        private fun deleteItem(image: ImageRVModel) {
            deleteImage?.invoke(image)
            this@ImagesRVAdapter.remove(image)
        }

        private fun setItemStatus(isSelected: Boolean ,image: ImageRVModel) {
            deleteButton.visibility = if (isSelected) View.VISIBLE else View.GONE
            image.isSelected = isSelected
        }

        private fun checkImageIsSelected(image: ImageRVModel) {
            if (image.isSelected) {
                deleteButton.visibility = View.VISIBLE
            } else {
                deleteButton.visibility = View.GONE
            }
        }
    }

    fun onImageClicked(listener: () -> Unit) {
        this.onImageClicked = listener
    }

    fun deleteImage(listener: (ImageRVModel) -> Unit) {
        this.deleteImage = listener
    }

    override fun convert(holder: ImagesVewHolder, item: ImageRVModel) {
        holder.bind(item)
    }
}