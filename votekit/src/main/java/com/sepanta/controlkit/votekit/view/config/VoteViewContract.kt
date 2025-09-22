package com.sepanta.controlkit.votekit.view.config

import androidx.compose.runtime.Composable
import com.sepanta.controlkit.votekit.service.model.VoteResponse
import com.sepanta.controlkit.votekit.view.viewModel.VoteViewModel

interface VoteViewContract {
    @Composable
    fun ShowView(config: VoteViewConfig, response: VoteResponse,viewModel: VoteViewModel)
}