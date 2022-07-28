package com.testtask.testnatife.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.testtask.testnatife.R
import com.testtask.testnatife.di.DaggerAppComponent
import com.testtask.testnatife.presentation.contracts.Navigator
import com.testtask.testnatife.presentation.screens.MainScreenFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToMainScreen()
    }

    override fun goToMainScreen() {
        launchFragment(MainScreenFragment.newInstance(), false)
    }

    private fun launchFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_container, fragment, fragment::class.simpleName)
            if (isAddToBackStack) addToBackStack(fragment::class.simpleName)
            commit()
        }
    }
}