package com.example.stackapp.data.tags

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.stackapp.data.api.ErrorsHandler
import com.example.stackapp.data.api.StackAppRestClient
import com.example.stackapp.data.models.ResultResponse
import com.example.stackapp.data.models.tag.Tag
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class TagsDataSource constructor(
    private val stackAppRestClient: StackAppRestClient
) :
    PageKeyedDataSource<Int, Tag>() {

    var initialLoadStateLiveData = MutableLiveData<ResultResponse.Status>()
    var initialLoadErrorLiveData = MutableLiveData<Triple<ErrorsHandler.ApiError, String?, Int?>>()
    var nextLoadErrorLiveData = MutableLiveData<Triple<ErrorsHandler.ApiError, String?, Int?>>()
    var paginatedNetworkStateLiveData = MutableLiveData<ResultResponse.Status>()
    private val compositeDisposable = CompositeDisposable()
    private var page: Int = 1

    private fun onTagsFetched(tags: List<Tag>, callback: LoadInitialCallback<Int, Tag>) {
        initialLoadStateLiveData.postValue(ResultResponse.Status.SUCCESS)
        page = page.inc()
        callback.onResult(tags, page, page.dec())
    }

    private fun onNextTagsFetched(tags: List<Tag>, callback: LoadCallback<Int, Tag>) {
        paginatedNetworkStateLiveData.postValue(ResultResponse.Status.SUCCESS)
        page = page.inc()
        callback.onResult(tags, page)
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
        callback: LoadInitialCallback<Int, Tag>
    ) {
        initialLoadStateLiveData.postValue(ResultResponse.Status.LOADING)

        val loadFirstTags = getTags()
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
                            it.data?.let { it1 -> onTagsFetched(it1, callback) }
                            initialLoadStateLiveData.postValue(ResultResponse.Status.SUCCESS)
                        }
                    }
                }
        compositeDisposable.add(loadFirstTags)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Tag>) {
        paginatedNetworkStateLiveData.postValue(ResultResponse.Status.LOADING)

        val loadNextTags = getTags()
                .subscribe {
                    when (it.status) {
                        ResultResponse.Status.ERROR -> nextLoadErrorLiveData.postValue(it.error?.let { it1 ->
                            ErrorsHandler.parseNetworkError(
                                it1
                            )
                        })
                        ResultResponse.Status.SUCCESS -> {
                            it.data?.let { it1 -> onNextTagsFetched(it1, callback) }
                            paginatedNetworkStateLiveData.postValue(ResultResponse.Status.SUCCESS)
                        }
                    }
                }
        compositeDisposable.add(loadNextTags)
    }

    private fun getTags(): Observable<ResultResponse<List<Tag>>> {
        return stackAppRestClient.stackService.getTags(page, "stackoverflow", "desc")
            .map { t -> ResultResponse.success(t.items) }
            .onErrorReturn { t -> ResultResponse.error(t) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tag>) {}
}