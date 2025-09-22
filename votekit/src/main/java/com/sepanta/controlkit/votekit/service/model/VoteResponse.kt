package com.sepanta.controlkit.votekit.service.model


data class
VoteResponse(
    val id: String?=null,
    val name: String?=null,
    val title: String?=null,
    val buttonSubmit: String?=null,
    val buttonCancel: String?=null,
    val description: String?=null,
    val force: Boolean=false,
    val voteOptions: List<VoteOptions>?=null,

    val createdAt: String?=null)


