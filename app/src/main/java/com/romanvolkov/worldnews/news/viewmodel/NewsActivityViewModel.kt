package com.romanvolkov.worldnews.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romanvolkov.worldnews.*
import com.romanvolkov.worldnews.news.NewsItem
import com.romanvolkov.worldnews.repository.INewsRepository
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

interface INewsActivityViewModel : IViewModel {
    val state: LiveData<List<IViewObject>>
    fun load(page: Int)
    fun loadMore()
}

class NewsActivityViewModel(private val repository: INewsRepository) :
    INewsActivityViewModel, BaseViewModel() {

    private var currentPage = 2

    override val state: MutableLiveData<List<IViewObject>> = MutableLiveData(
        listOf(LoadingVO("loading"))
    )

    init {
        load(page = 1)
    }

    override fun load(page: Int) {
        repository
            .getTopNews(page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { state.postValue(listOf(LoadingVO("loading"))) }
            .subscribe(Consumer {
                state.postValue(
                    when (it) {
                        is Resource.Data ->
                            it.data.articles.map { news ->
                                NewsItem(
                                    id = news.url,
                                    title = StringVO.Plain(news.title),
                                    description = news.description?.let { description ->
                                        StringVO.Plain(description)
                                    } ?: StringVO.Plain(""),
                                    source = StringVO.Plain(news.source.name),
                                    url = news.url,
                                    imageUrl = news.urlToImage,
                                    publishedAt = news.publishedAt
                                )
                            }
                        is Resource.Error -> listOf(
                            ErrorVO(
                                id = "error",
                                message = it.error.localizedMessage?.let { str ->
                                    StringVO.Plain(str)
                                } ?: StringVO.Resource(R.string.oops)))
                    }
                )
            })
            .addTo(disposables)
    }

    override fun loadMore() {
        load(currentPage)
        currentPage++
    }

}