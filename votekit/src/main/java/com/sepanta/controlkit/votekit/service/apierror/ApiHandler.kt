package com.sepanta.controlkit.votekit.service.apierror

import com.sepanta.errorhandler.traceErrorException
import retrofit2.Response

suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = execute()
        NetworkResult.Success(response.body())
    } catch (e: Exception) {
        NetworkResult.Error(traceErrorException(e))
    }
}
