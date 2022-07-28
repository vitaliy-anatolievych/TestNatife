package com.testtask.testnatife.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testtask.testnatife.core.viewmodels.ViewModelFactory
import com.testtask.testnatife.core.viewmodels.ViewModelKey
import com.testtask.testnatife.presentation.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: MainViewModel): ViewModel
}
