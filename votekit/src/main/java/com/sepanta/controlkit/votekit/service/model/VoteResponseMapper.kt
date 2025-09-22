package com.sepanta.controlkit.votekit.service.model

import com.sepanta.controlkit.votekit.util.Utils.getContentBySystemLang
import java.util.Locale




fun ApiVoteResponse.toDomain(lang: String?): VoteResponse {
    val d = this.data
    return VoteResponse(
        id = d.id,
        title = d.title.getContentBySystemLang(lang),
        name =d.name ,
        force =d.force ?:false,
        description = d.description.getContentBySystemLang(lang),
        createdAt = d.created_at,
        voteOptions = d.vote_options?.map { it.convertToResponse() },
    )
}
data class ApiVoteResponse(
    val data: ApiData
)

data class ApiData(
    val id: String,
    val name: String?=null,
    val force: Boolean?=null,
    val title: List<LocalizedText>?=null,
    val description: List<LocalizedText>?=null,
    val sdk_version: String?=null,
    val vote_options: List<VoteOptionsApi>?=null,
    val created_at: String
)

data class LocalizedText(
    val language: String,
    val content: String
)

fun VoteOptionsApi.convertToResponse(): VoteOptions {
    val d = this
    return VoteOptions(
        id = d.id,
        title = d.title.getContentBySystemLang(),
        createdAt = d.created_at,
        order = d.order,
    )
}



data class VoteOptionsApi(
    val id: String,
    val title: List<LocalizedText>?=null,
    val order: Int,
    val created_at: String
)
