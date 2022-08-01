package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import com.testtask.testnatife.databinding.FragmentFullScreenImageBinding
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter
import com.testtask.testnatife.presentation.adapters.diffutils.ImageListDiffCallBack
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel
import com.testtask.testnatife.presentation.adapters.utils.LoadMoreViewVertical
import com.testtask.testnatife.presentation.core.BaseFragment
import com.testtask.testnatife.presentation.debugPrint

class FullImageScreenFragment : BaseFragment() {
    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding: FragmentFullScreenImageBinding
        get() = _binding ?: throw NullPointerException("FragmentFullScreenImageBinding is null")

    private lateinit var rvAdapter: ImagesRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullScreenImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingRecyclerView()
    }

    private fun settingRecyclerView() {
        rvAdapter = ImagesRVAdapter()
        binding.rvFullScreen.adapter = rvAdapter

        LinearSnapHelper().apply {
            attachToRecyclerView(binding.rvFullScreen)
        }

        rvAdapter.data = arguments?.getParcelableArrayList(IMAGES_DATA)
            ?: throw NullPointerException("FullImageScreenFragment: ImagesData not Found")

        rvAdapter.setDiffCallback(ImageListDiffCallBack())

        rvAdapter.loadMoreModule.loadMoreView = LoadMoreViewVertical()
        rvAdapter.loadMoreModule.isAutoLoadMore = true
        rvAdapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
            requireContext().debugPrint("LOAD MORE FullImageScreenFragment")
        }
    }

    private fun loadMore() {
        loadMoreImages?.invoke()?.run {
            val newData = mutableListOf<ImageRVModel>()
            newData.addAll(rvAdapter.data)
            newData.addAll(this)
            rvAdapter.setDiffNewData(newData)
            sayAdapterLoadDataSuccessful()
        }
    }

    private fun sayAdapterLoadDataSuccessful() {
        rvAdapter.loadMoreModule.loadMoreComplete()
        rvAdapter.loadMoreModule.isEnableLoadMore = true
    }

    companion object {
        private const val IMAGES_DATA = "image_adapter"
        var loadMoreImages: (() -> List<ImageRVModel>)? = null


        @JvmStatic
        fun newInstance(images: ArrayList<ImageRVModel>) =
            FullImageScreenFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(IMAGES_DATA, images)
                }
            }
    }
}