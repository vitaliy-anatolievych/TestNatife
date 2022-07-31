package com.testtask.testnatife.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun goToFullScreenImage(imageRVModel: ImageRVModel) {
        launchFragment(FullImageScreenFragment.newInstance(imageRVModel), isAddToBackStack = true, isNewTask = false)
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