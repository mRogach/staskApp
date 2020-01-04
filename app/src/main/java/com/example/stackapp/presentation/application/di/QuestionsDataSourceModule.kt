package com.example.stackapp.presentation.application.di

import androidx.paging.DataSource
import androidx.recyclerview.widget.DiffUtil
import com.example.stackapp.data.models.question.Question
import com.example.stackapp.data.questions.QuestionsDataSourceFactory
import com.example.stackapp.presentation.basics.adapter.BasePagedListAdapter
import com.example.stackapp.presentation.fragments.questions.adapter.QuestionsDiffCallback
import com.example.stackapp.presentation.fragments.questions.adapter.QuestionsPagedListAdapter
import dagger.Module
import dagger.Provides

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

@Module
class QuestionsDataSourceModule {

    @Provides
    fun provideQuestionsDataSourceFactory(questionsDataSourceFactory: QuestionsDataSourceFactory): DataSource.Factory<Int, Question> =
        questionsDataSourceFactory

    @Provides
    fun provideDiffUtilCallback(diffUtilCallback: QuestionsDiffCallback): DiffUtil.ItemCallback<Question> =
        diffUtilCallback

    @Provides
    fun provideAdapter(questionsAdapter: QuestionsPagedListAdapter): BasePagedListAdapter<Question, QuestionsPagedListAdapter.QuestionViewHolder> =
        questionsAdapter
}