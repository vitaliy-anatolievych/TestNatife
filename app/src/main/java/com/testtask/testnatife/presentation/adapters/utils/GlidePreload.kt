package com.testtask.testnatife.presentation.adapters.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.presentation.debugPrint

object GlidePreload {

    @Volatile
    private var downloadedImages = 0

    fun preloadImages(context: Context, list: List<ImageModel>, isLoaded: (Boolean) -> Unit) {

        list.forEach { image ->
            Thread {
                Glide.with(context)
                    .asGif()
                    .load(image.imageUrl)
                    .listener(object : RequestListener<GifDrawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            downloadedImages++
                            if (downloadedImages >= list.size) isLoaded.invoke(true)
                            context.debugPrint("onLoadFailed: $downloadedImages | ${list.size}")
                            return false
                        }

                        override fun onResourceReady(
                            resource: GifDrawable?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            downloadedImages++

                            if (downloadedImages >= list.size) isLoaded.invoke(true)
                            context.debugPrint("onResourceReady: downloaded images: $downloadedImages | list size: ${list.size}")
                            return false
                        }
                    }).preload()
            }.run()
        }
    }

    fun setNewPreload() {
        downloadedImages = 0
    }
}