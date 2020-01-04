package com.example.stackapp.data.models

/**
 * Created by
 * Mykhailo on 12/13/2019.
 */

data class ResultResponse<T> (val status: Status, val error: Throwable?, val data: T?) {

    companion object {
        fun <T> success(model : T) : ResultResponse<T> = ResultResponse(Status.SUCCESS, null, model)
        fun <T> error(error : Throwable) : ResultResponse<T> = ResultResponse(Status.ERROR, error, null)
        fun <T> loading() : ResultResponse<T> = ResultResponse(Status.LOADING, null, null)
    }

    enum class Status {
        LOADING, ERROR, SUCCESS
    }
}