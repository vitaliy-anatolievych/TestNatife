package com.testtask.testnatife.presentation.contracts

import androidx.fragment.app.Fragment
import com.testtask.testnatife.presentation.adapters.ImagesRVAdapter

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun goToMainScreen()

    fun goToFullScreenImage(imagesRVAdapter: ImagesRVAdapter)
}

