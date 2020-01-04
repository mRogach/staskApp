package com.example.stackapp.presentation.application.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

@Module(includes = [TagsDataSourceModule::class, QuestionsDataSourceModule::class])
class AppModule {

    @Singleton
    @Provides
    internal fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()
}