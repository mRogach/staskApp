package com.example.stackapp.presentation.application.di

import com.example.stackapp.presentation.activities.MainActivity
import com.example.stackapp.presentation.basics.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

@Module
abstract class ActivityProvider {

    @ContributesAndroidInjector()
    abstract fun bindBaseActivity(): BaseActivity

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity
}