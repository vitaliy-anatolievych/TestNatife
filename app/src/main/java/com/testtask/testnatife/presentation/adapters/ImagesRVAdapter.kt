package com.testtask.testnatife.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testtask.testnatife.R
import com.testtask.testnatife.databinding.ImageItemBinding
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.presentation.adapters.diffutils.ImageListDiffCallBack

class ImagesRVAdapter:
    ListAdapter<ImageModel, ImagesRVAdapter.ImagesVewHolder>(ImageListDiffCallBack()) {


    inner class ImagesVewHolder(view: View): RecyclerView.ViewHolder(view) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesVewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImagesVewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesVewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}