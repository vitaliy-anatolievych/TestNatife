package com.testtask.testnatife.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testtask.testnatife.R
import com.testtask.testnatife.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {
    private val component = DaggerAppComponent.factory().create(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}