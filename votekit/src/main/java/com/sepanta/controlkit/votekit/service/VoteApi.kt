package com.sepanta.controlkit.votekit.service

import com.sepanta.controlkit.votekit.service.apierror.NetworkResult
import com.sepanta.controlkit.votekit.service.apierror.handleApi
import com.sepanta.controlkit.votekit.service.model.ActionResponse
import com.sepanta.controlkit.votekit.service.model.ApiVoteResponse


class VoteApi(private val apiService: ApiService) {

    suspend fun getData(
        route: String, appId: String, version: String, deviceId: String,
        sdkVersion: String,
        name: String,
    ): NetworkResult<ApiVoteResponse> {
        return handleApi {
            apiService.getData(
                route,
                appId=appId,
                version=version,
                deviceId = deviceId,
                sdkVersion = sdkVersion,
                name = name,
            )
        }
    }
    suspend fun setViewAction(
        route: String,
        appId: String,
        version: String,
        deviceId: String,
        sdkVersion: String,
    ): NetworkResult<ActionResponse> {
        return handleApi {
            apiService.setViewAction(
                url = route,
                appId = appId,
                version = version,
                deviceId = deviceId,
                sdkVersion = sdkVersion,
            )
        }
    }
    suspend fun setSubmitAction(
        route: String,
        appId: String,
        version: String,
        deviceId: String,
        sdkVersion: String,
        optionId: String,
    ): NetworkResult<ActionResponse> {
        return handleApi {
            apiService.setSubmitAction(
                url = route,
                appId = appId,
                version = version,
                deviceId = deviceId,
                sdkVersion = sdkVersion,
                optionId = optionId
            )
        }
    }
}
