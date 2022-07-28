package com.testtask.testnatife.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.HandleOnce

abstract class BaseViewModel : ViewModel() {

    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failureData.value = HandleOnce(failure)
    }
}