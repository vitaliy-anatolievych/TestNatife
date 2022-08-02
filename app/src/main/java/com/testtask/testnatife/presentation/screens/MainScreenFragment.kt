package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.testtask.testnatife.R
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.core.viewmodels.onFailure
import com.testtask.testnatife.core.viewmodels.onSuccess
import com.testtask.testnatife.core.viewmodels.onSuccessOnce
import com.testtask.testnatife.databinding.FragmentMainScreenBinding
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.presentation.MainActivity
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter
import com.testtask.testnatife.presentation.adapters.diffutils.ImageListDiffCallBack
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel
import com.testtask.testnatife.presentation.adapters.utils.GlidePreload
import com.testtask.testnatife.presentation.adapters.utils.LoadMoreViewVertical
import com.testtask.testnatife.presentation.adapters.utils.NetworkStateListener
import com.testtask.testnatife.presentation.adapters.utils.RVAdapterMapper
import com.testtask.testnatife.presentation.contracts.navigator
import com.testtask.testnatife.presentation.core.BaseFragment
import com.testtask.testnatife.presentation.debugPrint
import com.testtask.testnatife.presentation.hideKeyboard
import com.testtask.testnatife.presentation.viewmodels.MainViewModel
import kotlin.properties.Delegates

class MainScreenFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding ?: throw NullPointerException("FragmentMainScreenBinding is null")

    private lateinit var imageAdapter: ImagesRVAdapter
    private var isNetworkAvailable: Boolean by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = viewModel {
            onSuccess(addToBlackListData, ::handleBlackList)
            onSuccessOnce(imagesData, ::handleImages)
            onFailure(failureData, ::handleFailure)
        }

        checkNetworkState()
    }

    private fun checkNetworkState() {
        isNetworkAvailable = NetworkStateListener.isOnline(this.requireContext())

        NetworkStateListener.networkState = { networkState ->
            requireContext().debugPrint("$networkState")
            isNetworkAvailable = networkState
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        binding.etSearchImage.setText(mainViewModel.currentQuery)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsAdapter()
        downloadImages()
    }

    private fun downloadImages() {
        binding.etSearchImage.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val query = textView.text.toString()

                    if (isNewQueryValid(query)) {
                        makeRequest(textView)
                    } else {
                        handleFailure(Failure.RequestIsEmptyOrOld)
                    }
                }
            }
            return@setOnEditorActionListener true
        }
    }

    private fun makeRequest(textView: TextView) {
        if (isNetworkAvailable) {
            showLoadProgress(isStateLoad = true)
            mainViewModel.getImages(textView.text.toString())
            requireContext().hideKeyboard(textView)
        } else {
            handleFailure(Failure.RequestFailure)
        }
    }

    private fun isNewQueryValid(query: String) =
        (query.isNotEmpty() && query != mainViewModel.currentQuery)

    private fun settingsAdapter() {
        imageAdapter = mainViewModel._stateAdapter.value ?: ImagesRVAdapter()
        binding.rvMainScreen.adapter = imageAdapter

        // Restore Adapter State
        mainViewModel._stateRecyclerView.value?.let {
            binding.rvMainScreen.layoutManager?.onRestoreInstanceState(it)
        }

        imageAdapter.setDiffCallback(ImageListDiffCallBack())

        imageAdapter.loadMoreModule.loadMoreView = LoadMoreViewVertical()
        imageAdapter.loadMoreModule.preLoadNumber = COUNT_OF_PRELOAD_IMAGES
        imageAdapter.loadMoreModule.isAutoLoadMore = true
        imageAdapter.loadMoreModule.setOnLoadMoreListener {
            requireContext().debugPrint("CLICK setOnLoadMoreListener")
            loadMore()
        }
        imageAdapter.deleteImage { addToBlackList(image = it) }
        imageAdapter.onImageClicked { openImageFullSize(it) }
    }

    private fun handleImages(images: List<ImageModel>?) {
        Log.e("IMAGES", "${images?.size}")
        Log.e("IMAGES", "$images")

        images?.let { newList ->
            GlidePreload.setNewPreload()
            GlidePreload.preloadImages(requireContext(), newList) { isLoaded ->
                if (isLoaded) {
                    showLoadProgress(isStateLoad = false)
                    val linkedList = mutableListOf<ImageRVModel>()
                    if (mainViewModel.isNextData) {
                        linkedList.addAll(
                            RVAdapterMapper.mapListImageModelToListImageRVModel(
                                newList
                            )
                        )
                        binding.rvMainScreen.smoothScrollToPosition(RecyclerView.SCROLL_INDICATOR_TOP)
                    } else {
                        linkedList.addAll(imageAdapter.data)
                        linkedList.addAll(
                            RVAdapterMapper.mapListImageModelToListImageRVModel(
                                newList
                            )
                        )
                    }
                    imageAdapter.setDiffNewData(linkedList.distinctBy { it.id } .toMutableList())
                    sayAdapterLoadDataSuccessful()
                }
            }
        }
    }

    private fun handleBlackList(none: None?) {
        Toast.makeText(
            requireContext(),
            getString(R.string.image_added_to_black_list),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun openImageFullSize(image: ImageRVModel) {
        navigator().goToFullScreenImage(image)
        this.onStop()
    }

    private fun addToBlackList(image: ImageRVModel) {
        mainViewModel.addImageToBlackList(RVAdapterMapper.mapImageRVModelToIMageModel(image))
    }

    private fun loadMore() = with(binding) {
        mainViewModel.getImages(etSearchImage.text.toString())
    }

    override fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnectionError -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.network_connection_error),
                    Toast.LENGTH_SHORT
                ).show()

                val query = binding.etSearchImage.text.toString()

                if (isNewQueryValid(query)) {
                    mainViewModel.loadImagesFromCache(query = query)
                }

                sayAdapterLoadDataFailure()
            }
            is Failure.RequestFailure -> {
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

            is Failure.RequestIsEmptyOrOld -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.request_empty_or_old),
                    Toast.LENGTH_SHORT
                ).show()

            }

            is Failure.ListEmpty -> {
                Toast.makeText(
                    requireContext(), getString(R.string.error_list_is_empty),
                    Toast.LENGTH_SHORT
                ).show()
                sayAdapterLoadDataFailure()
            }
            else -> {
                Toast.makeText(
                    requireContext(), getString(R.string.unknown_error),
                    Toast.LENGTH_SHORT
                ).show()

                sayAdapterLoadDataFailure()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveStateRecycler()
        sayAdapterLoadDataSuccessful()
    }

    private fun saveStateRecycler() {
        mainViewModel._stateAdapter.value = imageAdapter
        mainViewModel._stateRecyclerView.value =
            binding.rvMainScreen.layoutManager?.onSaveInstanceState()
    }

    private fun sayAdapterLoadDataSuccessful() {
        imageAdapter.loadMoreModule.loadMoreComplete()
        imageAdapter.loadMoreModule.isEnableLoadMore = true
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