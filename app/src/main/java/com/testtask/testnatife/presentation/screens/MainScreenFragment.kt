package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.testtask.testnatife.R
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.viewmodels.onFailure
import com.testtask.testnatife.core.viewmodels.onSuccess
import com.testtask.testnatife.databinding.FragmentMainScreenBinding
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter
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
                    if (textView.text.isNotEmpty()) {
                        mainViewModel.getImages(textView.text.toString())
                    }
                }
            }
            return@setOnEditorActionListener true
        }
    }

    private fun settingsAdapter() {
        imageAdapter = ImagesRVAdapter()
        binding.rvMainScreen.adapter = imageAdapter
    }

    private fun handleImages(images: List<ImageModel>?) {
        Log.e("IMAGES", "$images")
         imageAdapter.submitList(images)
    }

    override fun handleFailure(failure: Failure?) {
        super.handleFailure(failure)
        when(failure) {
            Failure.ListEmpty -> Toast.makeText(
                requireContext(), getString(R.string.error_list_is_empty),
                Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }
}