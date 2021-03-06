package com.robertas.ugithub.models.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "total_count")
    override val totalCount: Int,

    @Json(name = "incomplete_results")
    override val incompleteResults: Boolean,

    @Json(name = "items")
    override val items: List<UserNetwork>
) : ResponseContainer<UserNetwork>()
