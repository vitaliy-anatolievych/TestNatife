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

    private val _currentQuery = MutableLiveData<String>()
    val currentQuery: String
        get() = _currentQuery.value ?: ""

    fun getImages(query: String) {
        isNextData(query) // Если у нас новый запрос, сбросит значение offset

        getImagesUseCase(
            GetImages.Params(
               query = query,
                limit = IMAGES_COUNT,
                offset = IMAGES_COUNT * PAGE_ITERATOR,
                rating = "g",
                lang = "uk"
            )
        ) {
            it.either(::handleFailure, ::handleImages)
        }
    }

    private fun isNextData(query: String) {
        if (query == _currentQuery.value) {
            PAGE_ITERATOR++
        } else {
            PAGE_ITERATOR = 0
            _currentQuery.value = query
        }
    }

    private fun handleImages(images: List<ImageModel>) {
        _imagesData.value = images
    }

    override fun onCleared() {
        super.onCleared()
        getImagesUseCase.unsubscribe()
    }

    companion object {
        private const val IMAGES_COUNT = 10
        private var PAGE_ITERATOR = 0
    }
}