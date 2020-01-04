package com.example.stackapp.data.questions

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.stackapp.data.api.StackAppRestClient
import com.example.stackapp.data.models.question.Question
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class QuestionsDataSourceFactory @Inject constructor(private val stackAppRestClient: StackAppRestClient): DataSource.Factory<Int, Question>() {

    var selectedTag: String = ""
    val sourceLiveData = MutableLiveData<QuestionsDataSource>().apply { QuestionsDataSource(stackAppRestClient, selectedTag) }
    private lateinit var latestSource: QuestionsDataSource

    override fun create(): DataSource<Int, Question> {
        latestSource = QuestionsDataSource(stackAppRestClient, selectedTag)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}