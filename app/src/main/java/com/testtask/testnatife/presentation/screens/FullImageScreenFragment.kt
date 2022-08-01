package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testtask.testnatife.databinding.FragmentFullScreenImageBinding
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter
import com.testtask.testnatife.presentation.core.BaseFragment
import com.testtask.testnatife.presentation.debugPrint

class FullImageScreenFragment : BaseFragment() {
    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding: FragmentFullScreenImageBinding
        get() = _binding ?: throw NullPointerException("FragmentFullScreenImageBinding is null")

    private lateinit var rvAdapter: ImagesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().debugPrint("savedInstanceState: $arguments ")
        rvAdapter = arguments?.getParcelable<ImagesRVAdapter>(IMAGE_ADAPTER)
            ?: throw NullPointerException("FullImageScreenFragment: Adapter no Founded")
    }

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
        binding.rvFullScreen.adapter = rvAdapter
    }

    companion object {
        private const val IMAGE_ADAPTER = "image_adapter"

        @JvmStatic
        fun newInstance(imagesRVAdapter: ImagesRVAdapter) =
            FullImageScreenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IMAGE_ADAPTER, imagesRVAdapter)
                }
            }
    }
}