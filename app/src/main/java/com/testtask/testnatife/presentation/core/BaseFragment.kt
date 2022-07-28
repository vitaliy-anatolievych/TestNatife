package com.testtask.testnatife.presentation.core

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testtask.testnatife.R
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.di.DaggerAppComponent
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val component by lazy {
        DaggerAppComponent.factory().create(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(requireActivity(), viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    open fun handleFailure(failure: Failure?) {
        when(failure) {
            is Failure.NetworkConnectionError -> Toast.makeText(
                requireContext(),
                getString(R.string.network_connection_error),
                Toast.LENGTH_SHORT
            ).show()
            is Failure.ServerError -> Toast.makeText(
                requireContext(),
                getString(R.string.server_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}