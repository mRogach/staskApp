package com.example.stackapp.data.tags

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.stackapp.data.api.StackAppRestClient
import com.example.stackapp.data.models.tag.Tag
import javax.inject.Inject

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

class TagsDataSourceFactory @Inject constructor(private val stackAppRestClient: StackAppRestClient): DataSource.Factory<Int, Tag>() {
    val sourceLiveData = MutableLiveData<TagsDataSource>().apply { TagsDataSource(stackAppRestClient) }
    private lateinit var latestSource: TagsDataSource
    override fun create(): DataSource<Int, Tag> {
        latestSource = TagsDataSource(stackAppRestClient)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}