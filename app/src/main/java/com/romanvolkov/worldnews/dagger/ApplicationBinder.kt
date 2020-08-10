package com.romanvolkov.worldnews.dagger

import androidx.lifecycle.ViewModelProviders
import com.romanvolkov.worldnews.news.view.NewsActivity
import com.romanvolkov.worldnews.news.viewmodel.INewsActivityViewModel
import com.romanvolkov.worldnews.news.viewmodel.NewsActivityViewModel
import com.romanvolkov.worldnews.repository.INewsRepository
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBinder {

    @ContributesAndroidInjector(modules = [ActivityViewModelModule::class])
    abstract fun newsActivity(): NewsActivity
}

@Module
class ActivityViewModelModule {
    @Provides
    fun iNewsVM(
        activity: NewsActivity,
        repository: INewsRepository
    ): INewsActivityViewModel =
        ViewModelProviders.of(
            activity, AppViewModelFactory.forInstance {
                NewsActivityViewModel(repository)
            }
        ).get(NewsActivityViewModel::class.java)
}