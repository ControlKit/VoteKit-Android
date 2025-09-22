package com.sepanta.controlkit.votekit

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sepanta.controlkit.votekit.config.VoteServiceConfig
import com.sepanta.controlkit.votekit.service.ApiService
import com.sepanta.controlkit.votekit.service.RetrofitClientInstance
import com.sepanta.controlkit.votekit.service.VoteApi
import com.sepanta.controlkit.votekit.service.local.LocalDataSource
import com.sepanta.controlkit.votekit.service.model.VoteResponse
import com.sepanta.controlkit.votekit.util.UniqueUserIdProvider
import com.sepanta.controlkit.votekit.view.config.VoteViewStyle
import com.sepanta.controlkit.votekit.view.viewModel.VoteViewModel
import com.sepanta.controlkit.votekit.view.viewModel.VoteViewModelFactory
import com.sepanta.controlkit.votekit.view.viewModel.state.ViewModelState

class VoteKit(
    private var config: VoteServiceConfig ,
    context: Context? = null,

) {

    private var _viewModel: VoteViewModel? = null
    val viewModel: VoteViewModel
        get() = _viewModel ?: throw kotlin.IllegalStateException("ViewModel not initialized yet")

    init {
        context?.let { setupViewModel(it) }
    }

    private fun setupViewModel(context: Context) {
        val retrofit = RetrofitClientInstance.getRetrofitInstance(
            config.timeOut,
            config.maxRetry,
            config.timeRetryThreadSleep
        ) ?: return
        val localDataSource = LocalDataSource(context)

        val api = VoteApi(retrofit.create(ApiService::class.java))
        _viewModel = VoteViewModelFactory(api,localDataSource).create(VoteViewModel::class.java)
        if (config.deviceId == null) {
            config.deviceId = UniqueUserIdProvider.getOrCreateUserId(context)
            _viewModel?.setConfig(config)

        } else {
            _viewModel?.setConfig(config)

        }
    }


    @Composable
    internal fun ConfigureComposable(
        onDismiss: (() -> Unit)? = null,
        onState: ((ViewModelState) -> Unit)? = null
    ) {
        if (_viewModel == null) return
        val state = _viewModel?.state?.collectAsState()?.value
        val response = remember { mutableStateOf<VoteResponse?>(null) }

        LaunchedEffect(Unit) {
            _viewModel?.cancelButtonEvent?.collect {
                onDismiss?.invoke()
            }
        }
        InitView(response)

        when (state) {
            ViewModelState.Initial -> onState?.invoke(ViewModelState.Initial)

            ViewModelState.NoContent -> onState?.invoke(ViewModelState.NoContent)

            is ViewModelState.ShowView -> state.data?.let {
                state.data?.let {
                    response.value = it
                    onState?.invoke(ViewModelState.ShowView(it))
                    _viewModel?.showDialog()
                }


            }

            is ViewModelState.Error -> onState?.invoke(ViewModelState.Error(state.data))

            else -> Unit
        }
    }

    fun showView() {
        _viewModel?.getData()
    }
    @Composable
    private fun InitView(checkUpdateResponse: MutableState<VoteResponse?>) {
        checkUpdateResponse.value?.let { data ->
            VoteViewStyle.checkViewStyle(config.viewConfig.viewStyle)
                .ShowView(config = config.viewConfig, data, viewModel)

        }
    }
}

@Composable
fun voteKitHost(
    config: VoteServiceConfig,
    onDismiss: (() -> Unit)? = null,
    onState: ((ViewModelState) -> Unit)? = null
): VoteKit {
    val context = LocalContext.current

    val kit = remember { VoteKit(config,context) }
    kit.ConfigureComposable(onDismiss = onDismiss, onState = onState)
    return kit
}


