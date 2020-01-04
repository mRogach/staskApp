package com.example.stackapp.data.models.tag

import com.google.gson.annotations.SerializedName

/**
 * Created by
 * Mykhailo on 12/13/2019.
 */
data class TagsResponse(
    val items: List<Tag>,
    @SerializedName("has_more")
    val hasMore: Boolean
)