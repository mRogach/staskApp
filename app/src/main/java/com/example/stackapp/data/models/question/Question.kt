package com.example.stackapp.data.models.question

import com.google.gson.annotations.SerializedName

/**
 * Created by
 * Mykhailo on 12/13/2019.
 */
data class Question(
    @SerializedName("question_id")
    val id: Long,
    val title: String,
    val owner: Owner,
    @SerializedName("creation_date")
    val creationDate: Long,
    var dateForUI: String
)

data class Owner(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("profile_image")
    val profileImage: String
)