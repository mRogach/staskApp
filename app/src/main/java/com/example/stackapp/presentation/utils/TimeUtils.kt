package com.example.stackapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

const val PATTERN = "HH:mm dd.MM.yyyy"

fun Calendar.toDateString(pattern: String? = PATTERN): String{
    return  SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(this.time)
}

fun Long.toDateString(pattern: String? = null): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.toDateString(pattern)
}