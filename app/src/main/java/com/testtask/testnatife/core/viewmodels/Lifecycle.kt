package com.testtask.testnatife.core.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.HandleOnce

fun <T : Any, L : LiveData<T>> LifecycleOwner.onSuccess(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this) {
        it.getContentIfNotHandled()?.let(body)
    }
