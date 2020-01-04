package com.example.stackapp.data.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

object ErrorsHandler {

    fun parseNetworkError(throwable: Throwable): Triple<ApiError, String?, Int?> {
        if (throwable is HttpException) {
            val statusCode = throwable.code()
            return try {
                val msg = throwable.response()?.errorBody()?.string()
                val errorBody = if (msg == null) null else Gson().fromJson(msg, ErrorBody::class.java)
                if (errorBody != null)
                    Triple(ApiError.BACKEND, errorBody.getErrorMsg(), errorBody.code)
                else
                    Triple(ApiError.UNKNOWN, null, statusCode)
            } catch (e: IOException) {
                throwable.printStackTrace()
                Triple(ApiError.REQUEST, throwable.message, statusCode)
            } catch (e: JsonSyntaxException) {
                throwable.printStackTrace()
                Triple(ApiError.REQUEST, throwable.message, statusCode)
            }
        } else {
            throwable.printStackTrace()
            return when (throwable) {
                is SocketTimeoutException -> Triple(ApiError.TIMEOUT, null, null)
                is ConnectException, is UnknownHostException -> Triple(ApiError.CONNECTION, null, null)
                else -> Triple(ApiError.UNKNOWN, null, null)
            }
        }
    }

    enum class ApiError {
        UNKNOWN,
        BACKEND,
        CONNECTION,
        TIMEOUT,
        REQUEST
    }
}

data class ErrorBody(
    @SerializedName("status_code")
    val code: Int,
    val message: String?,
    @SerializedName("status_message")
    val errorMessage: String?
) {
    fun getErrorMsg() = message ?: errorMessage
}