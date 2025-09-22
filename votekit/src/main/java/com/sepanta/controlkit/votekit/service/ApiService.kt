package com.sepanta.controlkit.votekit.service

import com.sepanta.controlkit.votekit.service.model.ActionResponse
import com.sepanta.controlkit.votekit.service.model.ApiVoteResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET()
    suspend fun getData(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-device-uuid") deviceId: String?,
        @Header("x-sdk-version") sdkVersion: String?,
        @Query("name") name: String ,
    ): Response<ApiVoteResponse>


    @POST()
    suspend fun setViewAction(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-sdk-version") sdkVersion: String,
        @Header("x-device-uuid") deviceId: String?,
    ): Response<ActionResponse>
    @FormUrlEncoded
    @POST()
    suspend fun setSubmitAction(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-sdk-version") sdkVersion: String,
        @Header("x-device-uuid") deviceId: String?,
        @Field("vote_option_id") optionId: String,

        ): Response<ActionResponse>
}