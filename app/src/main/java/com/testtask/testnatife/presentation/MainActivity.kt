package com.testtask.testnatife.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.testtask.testnatife.R
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel
import com.testtask.testnatife.presentation.contracts.Navigator
import com.testtask.testnatife.presentation.screens.FullImageScreenFragment
import com.testtask.testnatife.presentation.screens.MainScreenFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToMainScreen()
    }

    override fun goToMainScreen() {
        launchFragment(MainScreenFragment.newInstance(), isAddToBackStack = false, isNewTask = true)
    }

    override fun goToFullScreenImage(image: ImageRVModel) {
        FullImageScreenFragment.newInstance(image)
            .show(supportFragmentManager, FullImageScreenFragment::class.simpleName)
    }

    private fun launchFragment(fragment: Fragment, isAddToBackStack: Boolean, isNewTask: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (isNewTask) replace(R.id.main_fragment_container, fragment, fragment::class.simpleName)
            else add(R.id.main_fragment_container, fragment, fragment::class.simpleName)
            if (isAddToBackStack) addToBackStack(fragment::class.simpleName)
            commit()
        }
    }
}

fun Context.debugPrint(str: String) {
    Log.e("DEBUG_PRINT", str)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
