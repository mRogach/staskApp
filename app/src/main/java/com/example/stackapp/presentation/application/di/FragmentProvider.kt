package com.example.stackapp.presentation.application.di

import com.example.stackapp.presentation.fragments.questions.QuestionsFragment
import com.example.stackapp.presentation.fragments.tags.TagsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector()
    abstract fun bindTagsFragment(): TagsFragment

    @ContributesAndroidInjector()
    abstract fun bindQuestionsFragment(): QuestionsFragment
}