package com.example.stackapp.data.questions

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.stackapp.data.api.ErrorsHandler
import com.example.stackapp.data.api.StackAppRestClient
import com.example.stackapp.data.models.ResultResponse
import com.example.stackapp.data.models.question.Question
import com.example.stackapp.presentation.utils.PATTERN
import com.example.stackapp.presentation.utils.toDateString
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class QuestionsDataSource constructor(
    private val stackAppRestClient: StackAppRestClient,
    val tag: String
) :
    PageKeyedDataSource<Int, Question>() {

    var initialLoadStateLiveData = MutableLiveData<ResultResponse.Status>()
    var initialLoadErrorLiveData = MutableLiveData<Triple<ErrorsHandler.ApiError, String?, Int?>>()
    var nextLoadErrorLiveData = MutableLiveData<Triple<ErrorsHandler.ApiError, String?, Int?>>()
    var paginatedNetworkStateLiveData = MutableLiveData<ResultResponse.Status>()
    private val compositeDisposable = CompositeDisposable()
    private var page: Int = 1

    private fun onQuestionsFetched(
        questions: List<Question>,
        callback: LoadInitialCallback<Int, Question>
    ) {
        initialLoadStateLiveData.postValue(ResultResponse.Status.SUCCESS)
        page = page.inc()
        callback.onResult(questions, page, page.dec())
    }

    private fun onNextQuestionsFetched(
        questions: List<Question>,
        callback: LoadCallback<Int, Question>
    ) {
        paginatedNetworkStateLiveData.postValue(ResultResponse.Status.SUCCESS)
        page = page.inc()
        callback.onResult(questions, page)
    }

    override fun invalidate() {
        super.invalidate()
        compositeDisposable.dispose()
        page = 1
    }

    fun clear() {
        compositeDisposable.clear()
        page = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Question>
    ) {
        initialLoadStateLiveData.postValue(ResultResponse.Status.LOADING)

        val loadFirstQuestions = getQuestions()
            .subscribe {
                when (it.status) {
                    ResultResponse.Status.ERROR -> {
                        initialLoadErrorLiveData.postValue(it.error?.let { it1 ->
                            ErrorsHandler.parseNetworkError(
                                it1
                            )
                        })
                        initialLoadStateLiveData.postValue(ResultResponse.Status.ERROR)
                    }
                    ResultResponse.Status.SUCCESS -> {
                        it.data?.let { it1 -> onQuestionsFetched(it1, callback) }
                        initialLoadStateLiveData.postValue(ResultResponse.Status.SUCCESS)
                    }
                }
            }
        compositeDisposable.add(loadFirstQuestions)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Question>) {
        paginatedNetworkStateLiveData.postValue(ResultResponse.Status.LOADING)

        val loadNextQuestions = getQuestions()
            .subscribe {
                when (it.status) {
                    ResultResponse.Status.ERROR -> nextLoadErrorLiveData.postValue(it.error?.let { it1 ->
                        ErrorsHandler.parseNetworkError(
                            it1
                        )
                    })
                    ResultResponse.Status.SUCCESS -> {
                        it.data?.let { it1 -> onNextQuestionsFetched(it1, callback) }
                        paginatedNetworkStateLiveData.postValue(ResultResponse.Status.SUCCESS)
                    }
                }
            }
        compositeDisposable.add(loadNextQuestions)
    }

    private fun getQuestions(): Observable<ResultResponse<List<Question>>> {
        return stackAppRestClient.stackService.getQuestions(tag, page, "stackoverflow")
            .map { t ->
                ResultResponse.success(t.items.map {
                    it.apply {
                        it.dateForUI =
                            TimeUnit.SECONDS.toMillis(it.creationDate).toDateString(PATTERN)
                    }
                })
            }
            .onErrorReturn { t -> ResultResponse.error(t) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Question>) {}
}