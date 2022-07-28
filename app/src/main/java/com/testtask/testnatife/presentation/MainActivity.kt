package com.testtask.testnatife.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testtask.testnatife.R
import com.testtask.testnatife.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}