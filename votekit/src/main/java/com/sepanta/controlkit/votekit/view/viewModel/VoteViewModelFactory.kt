package com.sepanta.controlkit.votekit.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sepanta.controlkit.votekit.service.VoteApi
import com.sepanta.controlkit.votekit.service.local.LocalDataSource
import kotlin.jvm.java

class VoteViewModelFactory(
    private val api: VoteApi,
    private val localDataSource: LocalDataSource

    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VoteViewModel(api,localDataSource) as T
        }
        throw kotlin.IllegalArgumentException("Unknown ViewModel class")
    }
}