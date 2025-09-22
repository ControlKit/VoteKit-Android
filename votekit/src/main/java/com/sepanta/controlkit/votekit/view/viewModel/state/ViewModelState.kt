package com.sepanta.controlkit.votekit.view.viewModel.state

import com.sepanta.controlkit.votekit.service.model.VoteResponse
import com.sepanta.errorhandler.ApiError


sealed class ViewModelState {
    object Initial : ViewModelState()
    object NoContent : ViewModelState()
    data class ShowView(val data: VoteResponse?) : ViewModelState()
    data class Action(val data: Boolean?) : ViewModelState()
    data class Error(val data: ApiError<*>?) : ViewModelState()
    data class ActionError(val data: ApiError<*>?) : ViewModelState()


}

