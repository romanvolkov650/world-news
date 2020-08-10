package com.romanvolkov.worldnews.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import com.romanvolkov.worldnews.*
import com.romanvolkov.worldnews.entity.Article
import com.romanvolkov.worldnews.news.NewsItem
import com.romanvolkov.worldnews.repository.INewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface INewsActivityViewModel : IViewModel {
    val state: LiveData<List<IValueObject>>
    fun load(page: Int)
    fun loadMore()
}

class NewsActivityViewModel(private val repository: INewsRepository) :
    INewsActivityViewModel, BaseViewModel() {

    private var currentPage = 1
    private var currentLoadingPage: Int? = null
    private var maxPages = 1

    private val items: BehaviorRelay<List<Article>> = BehaviorRelay.createDefault(listOf())


    override val state: MutableLiveData<List<IValueObject>> = MutableLiveData(
        listOf(LoadingVO("loading"))
    )

    init {
        load(page = currentPage)
        items
            .observeOn(Schedulers.computation())
            .map { list ->
                list.map { article ->
                    NewsItem(
                        id = article.url,
                        title = StringVO.Plain(article.title),
                        description = article.description?.let { description ->
                            StringVO.Plain(description)
                        } ?: StringVO.Plain(""),
                        source = StringVO.Plain(article.source.name),
                        url = article.url,
                        imageUrl = article.urlToImage,
                        publishedAt = article.publishedAt
                    )
                }
            }
            .subscribe {
                state.postValue(it)
            }
            .addTo(disposables)
    }

    override fun load(page: Int) {
        if (currentLoadingPage == currentPage) return
        currentLoadingPage = page
        repository
            .getTopNews(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { resource ->
                currentLoadingPage = null
                when (resource) {
                    is Resource.Data -> {
                        currentPage++
                        maxPages = (resource.data.totalResults / 3)
                        val list = items.value?.toMutableList() ?: ArrayList()
                        list.addAll(resource.data.articles)
                        items.accept(list)
                    }
                    is Resource.Error -> {
                        state.postValue(
                            listOf(
                                ErrorVO(id = "error",
                                    message = resource.error.localizedMessage?.let {
                                        StringVO.Plain(
                                            it
                                        )
                                    } ?: StringVO.Resource(R.string.oops)
                                )
                            )
                        )
                    }
                }
            }
            .subscribe()
            .addTo(disposables)
    }

    override fun loadMore() {
        load(currentPage)
    }

}