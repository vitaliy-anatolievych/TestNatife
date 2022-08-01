package com.testtask.testnatife.presentation.viewmodels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.testtask.testnatife.core.type.HandleOnce
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.core.viewmodels.BaseViewModel
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.domain.usecases.AddImageToBlackList
import com.testtask.testnatife.domain.usecases.GetImages
import com.testtask.testnatife.domain.usecases.LoadImagesFromCache
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getImagesUseCase: GetImages,
    private val addImageToBlackListUseCase: AddImageToBlackList,
    private val loadImagesFromCacheUseCase: LoadImagesFromCache
) : BaseViewModel() {

    private val _imagesData: MutableLiveData<HandleOnce<List<ImageModel>>> = MutableLiveData()
    val imagesData: LiveData<HandleOnce<List<ImageModel>>>
        get() = _imagesData

    private val _currentQuery = MutableLiveData<String>()
    val currentQuery: String
        get() = _currentQuery.value ?: ""

    private val _addToBlackListData = MutableLiveData<None>()
    val addToBlackListData: LiveData<None>
        get() = _addToBlackListData

    private val _isNextData = MutableLiveData<Boolean>()
    val isNextData: Boolean
        get() = _isNextData.value ?: false

    val _stateAdapter = MutableLiveData<ImagesRVAdapter>()

    val _stateRecyclerView = MutableLiveData<Parcelable>()

    fun getImages(query: String) {
        _isNextData.value = isNextData(query) // Если у нас новый запрос, сбросит значение offset

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

    fun addImageToBlackList(image: ImageModel) {
        addImageToBlackListUseCase(image) {
            it.either(::handleFailure, ::handleBlackList)
        }
    }

    fun loadImagesFromCache(query: String) {
        loadImagesFromCacheUseCase(LoadImagesFromCache.Params(query)) {
            it.either(::handleFailure, ::handleImages)
        }
    }

    private fun isNextData(query: String): Boolean {
        return if (query == _currentQuery.value) {
            PAGE_ITERATOR++
            false
        } else {
            PAGE_ITERATOR = 0
            _currentQuery.value = query
            true
        }
    }

    private fun handleBlackList(none: None) {
        _addToBlackListData.value = none
    }

    private fun handleImages(images: List<ImageModel>) {
        _imagesData.value = HandleOnce(images)
    }

    override fun onCleared() {
        super.onCleared()
        getImagesUseCase.unsubscribe()
        addImageToBlackListUseCase.unsubscribe()
    }

    companion object {
        private const val IMAGES_COUNT = 10
        private var PAGE_ITERATOR = 0
    }
}