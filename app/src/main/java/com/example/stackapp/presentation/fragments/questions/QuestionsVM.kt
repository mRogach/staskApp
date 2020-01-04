package com.example.stackapp.presentation.fragments.questions

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.stackapp.data.api.ErrorsHandler
import com.example.stackapp.data.models.ResultResponse
import com.example.stackapp.data.models.question.Question
import com.example.stackapp.data.questions.QuestionsDataSourceFactory
import com.example.stackapp.presentation.basics.BaseViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class QuestionsVM @Inject constructor(
    val context: Context,
    val questionsDataSourceFactory: QuestionsDataSourceFactory
) : BaseViewModel(context) {

    var questions: LiveData<PagedList<Question>>? = null
    var selectedTag: String = ""

    fun startConfigureLoad() {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .build()
        questions = LivePagedListBuilder(questionsDataSourceFactory, config).build()
        questionsDataSourceFactory.selectedTag = selectedTag
    }

    fun initialLoadState(): MutableLiveData<ResultResponse.Status>? {
        return questionsDataSourceFactory.sourceLiveData.value?.initialLoadStateLiveData
    }

    fun getInitLoadError(): MutableLiveData<Triple<ErrorsHandler.ApiError, String?, Int?>>? {
        return questionsDataSourceFactory.sourceLiveData.value?.initialLoadErrorLiveData
    }

    fun getNextLoadError(): MutableLiveData<Triple<ErrorsHandler.ApiError, String?, Int?>>? {
        return questionsDataSourceFactory.sourceLiveData.value?.nextLoadErrorLiveData
    }

    fun retry() {
        questionsDataSourceFactory.sourceLiveData.value?.invalidate()
        startConfigureLoad()
        isNetworkError.value = false
        isLoading.value = true
    }

    fun listenConnectivity() {
        disposable.add(ReactiveNetwork
            .observeInternetConnectivity()
            .skip(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ if (it) {retry()} })
    }

    fun observeInitCommentsState(status: ResultResponse.Status) {
        when (status) {
            ResultResponse.Status.SUCCESS -> {
                isNetworkError.value = false
                isLoading.value = false
            }
            ResultResponse.Status.LOADING -> {
                isNetworkError.value = false
                isLoading.value = true
            }
            ResultResponse.Status.ERROR -> {
                isNetworkError.value = true
                isLoading.value = false
            }
        }
    }

    fun observeInitError(error: Triple<ErrorsHandler.ApiError, String?, Int?>) {
        errorMessage.value = convertError(error).first
    }

    fun observeNextError(error: Triple<ErrorsHandler.ApiError, String?, Int?>) {
        Toast.makeText(context, convertError(error).first, Toast.LENGTH_LONG).show()
    }
}