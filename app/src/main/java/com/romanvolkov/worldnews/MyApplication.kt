package com.romanvolkov.worldnews

import android.app.Application
import com.romanvolkov.worldnews.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)

        super.onCreate()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}