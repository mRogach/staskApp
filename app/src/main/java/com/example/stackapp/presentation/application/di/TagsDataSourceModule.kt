package com.example.stackapp.presentation.application.di

import androidx.paging.DataSource
import androidx.recyclerview.widget.DiffUtil
import com.example.stackapp.data.models.tag.Tag
import com.example.stackapp.data.tags.TagsDataSourceFactory
import com.example.stackapp.presentation.basics.adapter.BasePagedListAdapter
import com.example.stackapp.presentation.fragments.tags.adapter.TagsDiffCallback
import com.example.stackapp.presentation.fragments.tags.adapter.TagsPagedListAdapter
import dagger.Module
import dagger.Provides

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

@Module
class TagsDataSourceModule {

    @Provides
    fun provideTagsDataSourceFactory(tagsDataSourceFactory: TagsDataSourceFactory): DataSource.Factory<Int, Tag> =
        tagsDataSourceFactory

    @Provides
    fun provideDiffUtilCallback(diffUtilCallback: TagsDiffCallback): DiffUtil.ItemCallback<Tag> =
        diffUtilCallback

    @Provides
    fun provideAdapter(tagsAdapter: TagsPagedListAdapter): BasePagedListAdapter<Tag, TagsPagedListAdapter.TagViewHolder> =
        tagsAdapter
}