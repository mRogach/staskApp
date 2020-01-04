package com.example.stackapp.data.api

import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class StackAppRestClient @Inject constructor() : BaseRestClient() {

    lateinit var stackService: StackService

    init {
        setUpRestClient()
    }

    private fun setUpRestClient() {
        val retrofit = getRetrofitBuilder().client(getOkHttpClient()).build()
        stackService = retrofit.create(StackService::class.java)
    }
}