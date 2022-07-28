package com.testtask.testnatife.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.testnatife.core.viewmodels.BaseViewModel
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.domain.usecases.GetImages
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getImagesUseCase: GetImages
): BaseViewModel() {

    private val _imagesData = MutableLiveData<List<ImageModel>>()
    val imagesData: LiveData<List<ImageModel>>
        get() = _imagesData

    fun getImages(query: String) {
        getImagesUseCase(
            GetImages.Params(
               query = query,
                limit = 20,
                offset = 0,
                rating = "g",
                lang = "uk"
            )
        ) {
            it.either(::handleFailure, ::handleImages)
        }
    }

    private fun handleImages(images: List<ImageModel>) {
        _imagesData.value = images
    }
}