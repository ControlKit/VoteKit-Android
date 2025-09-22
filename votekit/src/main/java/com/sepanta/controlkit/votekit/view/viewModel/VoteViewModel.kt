package com.sepanta.controlkit.votekit.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sepanta.controlkit.votekit.BuildConfig
import com.sepanta.controlkit.votekit.config.VoteServiceConfig
import com.sepanta.controlkit.votekit.service.VoteApi
import com.sepanta.controlkit.votekit.service.apierror.NetworkResult
import com.sepanta.controlkit.votekit.service.local.LocalDataSource
import com.sepanta.controlkit.votekit.service.model.VoteRequestAnswer
import com.sepanta.controlkit.votekit.service.model.VoteResponse
import com.sepanta.controlkit.votekit.service.model.toDomain
import com.sepanta.controlkit.votekit.view.viewModel.state.ViewModelState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID

class VoteViewModel(
    private val api: VoteApi,
    private val localDataSource: LocalDataSource,
) : ViewModel() {
    private var itemId: String? = null
    private var forceVote = false
    private val url = BuildConfig.API_URL


    private var config: VoteServiceConfig? = null
    fun setConfig(config: VoteServiceConfig) {
        this.config = config
    }

    private val _mutableState = MutableStateFlow<ViewModelState>(ViewModelState.Initial)
    val state: StateFlow<ViewModelState> = _mutableState.asStateFlow()

    fun clearState() {
        _mutableState.value = ViewModelState.Initial
    }

    fun setViewAction() {
        if (itemId == null) return
        viewModelScope.launch {
            if (forceVote) {
                saveLastId(itemId)
            }
            api.setViewAction(
                url + "/${itemId}/view",
                config!!.appId,
                config!!.version,
                config!!.deviceId ?: "",
                BuildConfig.LIB_VERSION_NAME,
            )

        }
    }

    fun setSubmitAction() {
        if (voteRequestAnswer == null) return
        viewModelScope.launch {
            val data = api.setSubmitAction(
                url + "/${itemId}/submit",
                config!!.appId,
                config!!.version,
                config!!.deviceId ?: "",
                BuildConfig.LIB_VERSION_NAME,
                voteRequestAnswer!!.answerId.toString(),
            )
            when (data) {
                is NetworkResult.Success -> {
                    _mutableState.value = ViewModelState.Action(forceVote)
                    if (!forceVote) {
                        saveLastId(itemId)
                    }
                }

                is NetworkResult.Error -> {
                    _mutableState.value = ViewModelState.ActionError(data.error)
                }
            }

        }
    }

    fun getData() {
        if (state.value != ViewModelState.Initial || config == null) return
        viewModelScope.launch {
            val data = api.getData(
                url,
                config!!.appId,
                config!!.version,
                config!!.deviceId ?: "",
                BuildConfig.LIB_VERSION_NAME,
                config!!.name
            )

            when (data) {
                is NetworkResult.Success -> {
                    val response = data.value?.toDomain(config?.lang)
                    forceVote = response?.force ?: false
                    handleResponse(response)

                }

                is NetworkResult.Error -> {

                    _mutableState.value = ViewModelState.Error(data.error)
                }
            }
        }

    }

    private suspend fun saveLastId(lastId: String?) {

        lastId?.let { localDataSource.saveLastId(lastId) }
    }

    private suspend fun getLastId(): String? {
        return localDataSource.getLastId()
    }

    private suspend fun handleResponse(response: VoteResponse?) {
        if (response == null || response.id == null) {
            _mutableState.value = ViewModelState.NoContent
            return
        }

        val lastId = getLastId()
        val shouldShowAlert = when {
            lastId == null -> true
            else -> {
                val lastUuid = UUID.fromString(lastId)
                val newUuid = UUID.fromString(response.id)
                newUuid > lastUuid
            }
        }
        showAlert(response)

//        if (shouldShowAlert) {
//            showAlert(response)
//        } else {
//            _mutableState.value = ViewModelState.NoContent
//        }
    }

    private fun showAlert(response: VoteResponse) {
        itemId = response.id
        _mutableState.value = ViewModelState.ShowView(response)
        setViewAction()
    }

    private val _openDialog = MutableStateFlow(true)
    val openDialog: StateFlow<Boolean> = _openDialog.asStateFlow()

    fun showDialog() {
        _openDialog.value = true
    }


    fun dismissDialog() {
        _openDialog.value = false
        triggerLaunchAlert()
        clearState()

    }


    private val _cancelButtonEvent = Channel<Unit>(Channel.BUFFERED)
    val cancelButtonEvent = _cancelButtonEvent.receiveAsFlow()

    fun triggerLaunchAlert() {
        viewModelScope.launch {
            _cancelButtonEvent.send(Unit)
        }

    }


    private var _showError = true
    val showError: Boolean = _showError
    private var voteRequestAnswer: VoteRequestAnswer? = null
    fun checkAnswer(): Boolean {
        return _showError

    }

    fun submit() {
        setSubmitAction()
        _openDialog.value = false
        clearState()
    }

    fun setAnswer(voteRequestAnswer: VoteRequestAnswer) {
        _showError = false
        this.voteRequestAnswer = voteRequestAnswer
    }


}