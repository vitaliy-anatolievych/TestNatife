package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel
import com.testtask.testnatife.presentation.core.BaseFragment

class FullImageScreenFragment: BaseFragment() {


    companion object {
        private const val IMAGE_BUNDLE = "image_bundle"

        @JvmStatic
        fun newInstance(imageRVModel: ImageRVModel) =
            FullImageScreenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IMAGE_BUNDLE, imageRVModel)
                }
            }
    }
}