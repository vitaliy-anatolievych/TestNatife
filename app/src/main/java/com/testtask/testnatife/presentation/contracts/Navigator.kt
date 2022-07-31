package com.testtask.testnatife.presentation.contracts

import androidx.fragment.app.Fragment
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun goToMainScreen()

    fun goToFullScreenImage(imageRVModel: ImageRVModel)
}

