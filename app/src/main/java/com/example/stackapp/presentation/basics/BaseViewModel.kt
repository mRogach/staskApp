package com.example.stackapp.presentation.basics

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackapp.R
import com.example.stackapp.data.api.ErrorsHandler
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

open class BaseViewModel(private val context: Context) : ViewModel() {

    var isNetworkError = MutableLiveData<Boolean>()
    var isContentError = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    protected val disposable = CompositeDisposable()

    fun convertError(apiError: Triple<ErrorsHandler.ApiError, String?, Int?>): Pair<String?, Int?> {
        val resources = context.resources
        return Pair(
            when (apiError.first) {
                ErrorsHandler.ApiError.CONNECTION -> resources?.getString(R.string.error_msg_connection)
                ErrorsHandler.ApiError.TIMEOUT -> resources?.getString(R.string.error_msg_timeout)
                ErrorsHandler.ApiError.UNKNOWN -> resources?.getString(R.string.error_msg_unknown)
                ErrorsHandler.ApiError.BACKEND, ErrorsHandler.ApiError.REQUEST -> apiError.second
            }, apiError.third
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}