package com.testtask.testnatife.presentation.contracts

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun goToMainScreen()
}

