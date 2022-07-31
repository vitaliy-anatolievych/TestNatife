package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.testtask.testnatife.R
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.core.viewmodels.onFailure
import com.testtask.testnatife.core.viewmodels.onSuccess
import com.testtask.testnatife.databinding.FragmentMainScreenBinding
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter
import com.testtask.testnatife.presentation.adapters.diffutils.ImageListDiffCallBack
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel
import com.testtask.testnatife.presentation.adapters.utils.GlidePreload
import com.testtask.testnatife.presentation.adapters.utils.LoadMoreViewVertical
import com.testtask.testnatife.presentation.adapters.utils.RVAdapterMapper
import com.testtask.testnatife.presentation.core.BaseFragment
import com.testtask.testnatife.presentation.viewmodels.MainViewModel

class MainScreenFragment: BaseFragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw NullPointerException("FragmentMainScreenBinding is null")

    private lateinit var imageAdapter: ImagesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = viewModel {
            onSuccess(addToBlackListData, ::handleBlackList)
            onSuccess(imagesData, ::handleImages)
            onFailure(failureData, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsAdapter()
        downloadImages()
    }

    private fun downloadImages() {
        binding.etSearchImage.setOnEditorActionListener { textView, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val query = textView.text.toString()
                    if (isNewQueryValid(query)) {
                        showLoadProgress(isStateLoad = true)
                        mainViewModel.getImages(textView.text.toString())
                    }
                }
            }
            return@setOnEditorActionListener true
        }
    }

    private fun isNewQueryValid(query: String) =
        (query.isNotEmpty() && mainViewModel.currentQuery != query)

    private fun settingsAdapter() {
        imageAdapter = ImagesRVAdapter()
        imageAdapter.setDiffCallback(ImageListDiffCallBack())
        binding.rvMainScreen.adapter = imageAdapter

        imageAdapter.loadMoreModule.loadMoreView = LoadMoreViewVertical()
        imageAdapter.loadMoreModule.preLoadNumber = COUNT_OF_PRELOAD_IMAGES
        imageAdapter.loadMoreModule.isAutoLoadMore = true
        imageAdapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        imageAdapter.deleteImage = { addToBlackList(image = it) }
    }

    private fun handleImages(images: List<ImageModel>?) {
        Log.e("IMAGES", "${images?.size}")
        Log.e("IMAGES", "$images")

        images?.let { newList->
            GlidePreload.preloadImages(requireContext(), newList) { isLoaded ->
                if (isLoaded) {
                    showLoadProgress(isStateLoad = false)
                    // TODO Остался баг с обновлением списка
                    val linkedList = mutableListOf<ImageRVModel>()
                    linkedList.addAll(imageAdapter.data)
                    linkedList.addAll(RVAdapterMapper.mapListImageModelToListImageRVModel(newList))
                    imageAdapter.setDiffNewData(linkedList.toMutableList())
                    sayAdapterLoadDataSuccessful()
                }
            }
        }
    }

    private fun handleBlackList(none: None?) {
        Toast.makeText(requireContext(), getString(R.string.image_added_to_black_list), Toast.LENGTH_SHORT).show()
    }

    private fun addToBlackList(image: ImageRVModel) {
        mainViewModel.addImageToBlackList(RVAdapterMapper.mapImageRVModelToIMageModel(image))
    }

    private fun loadMore() = with(binding){
        mainViewModel.getImages(etSearchImage.text.toString())
    }

    override fun handleFailure(failure: Failure?) {
        when(failure) {
            is Failure.NetworkConnectionError -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_connection_error),
                    Toast.LENGTH_SHORT
                ).show()

                sayAdapterLoadDataFailure()
            }
            is Failure.ServerError -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.server_error),
                    Toast.LENGTH_SHORT
                ).show()

                sayAdapterLoadDataFailure()
            }

            is Failure.ListEmpty -> {
                Toast.makeText(
                    requireContext(), getString(R.string.error_list_is_empty),
                    Toast.LENGTH_SHORT).show()
                sayAdapterLoadDataFailure()
            }
            else -> {
                Toast.makeText(
                    requireContext(), getString(R.string.unknown_error),
                    Toast.LENGTH_SHORT).show()

                sayAdapterLoadDataFailure()
            }
        }
    }

    private fun sayAdapterLoadDataSuccessful() {
        imageAdapter.loadMoreModule.isEnableLoadMore = true
        imageAdapter.loadMoreModule.loadMoreComplete()
    }

    private fun sayAdapterLoadDataFailure() {
        imageAdapter.loadMoreModule.loadMoreFail()
        showLoadProgress(isStateLoad = false)
    }

    private fun showLoadProgress(isStateLoad: Boolean) {
        binding.pbStateLoad.visibility = if (isStateLoad) View.VISIBLE else View.GONE
    }

    companion object {
        private const val COUNT_OF_PRELOAD_IMAGES = 10

        @JvmStatic
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }
}