package com.sepanta.controlkit.votekit.config

import com.sepanta.controlkit.votekit.view.config.VoteViewConfig


data class VoteServiceConfig(
    var viewConfig: VoteViewConfig = VoteViewConfig(),
    var version: String = "0.0.0",
    var name: String,
    var appId: String,
    var lang: String = "en",
    var deviceId: String?=null,
    var timeOut: Long = 5000L,
    var timeRetryThreadSleep: Long = 1000L,
    var maxRetry: Int = 5,
    )