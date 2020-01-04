package com.example.stackapp.data.models.question

import com.google.gson.annotations.SerializedName

/**
 * Created by
 * Mykhailo on 12/13/2019.
 */
data class QuestionsResponse(
    val items: List<Question>,
    @SerializedName("has_more")
    val hasMore: Boolean
)