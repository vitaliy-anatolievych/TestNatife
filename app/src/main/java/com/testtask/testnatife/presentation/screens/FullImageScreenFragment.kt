package com.testtask.testnatife.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.testtask.testnatife.databinding.FragmentFullScreenImageBinding
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel

class FullImageScreenFragment : DialogFragment() {
    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding: FragmentFullScreenImageBinding
        get() = _binding ?: throw NullPointerException("FragmentFullScreenImageBinding is null")

    private lateinit var image: ImageRVModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = arguments?.getParcelable(IMAGE)
            ?: throw NullPointerException("FullImageScreenFragment: image is null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullScreenImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(dialog?.window?.decorView!!)
            .asGif()
            .load(image.imageUrl)
            .into(binding.ivImageFull)
    }

    companion object {
        private const val IMAGE = "image_full"


        @JvmStatic
        fun newInstance(image: ImageRVModel) =
            FullImageScreenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IMAGE, image)
                }
            }
    }
}