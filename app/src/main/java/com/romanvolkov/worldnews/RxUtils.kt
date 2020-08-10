package com.romanvolkov.worldnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Disposable.addTo(composite: CompositeDisposable) {
    composite.add(this)
}

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applySchedulers(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.applySchedulers(): Completable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy = BackpressureStrategy.LATEST): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}