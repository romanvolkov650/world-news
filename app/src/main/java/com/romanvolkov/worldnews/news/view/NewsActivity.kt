package com.romanvolkov.worldnews.news.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.airbnb.epoxy.EpoxyRecyclerView
import com.romanvolkov.worldnews.*
import com.romanvolkov.worldnews.news.NewsItem
import com.romanvolkov.worldnews.news.holder.NewsItemEpoxyHolder_
import com.romanvolkov.worldnews.news.viewmodel.INewsActivityViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


class NewsActivity : AppCompatActivity() {

    private lateinit var newsEpoxy: EpoxyRecyclerView

    @Inject
    lateinit var viewModel: INewsActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsEpoxy = findViewById(R.id.newsEpoxy)
        viewModel.state.observe(this, Observer(::updateItems))
    }

    private fun updateItems(items: List<IViewObject>) {
        newsEpoxy.withModels {
            items.forEach { item ->
                when (item) {
                    is NewsItem -> {
                        NewsItemEpoxyHolder_()
                            .id(item.id)
                            .item(item)
                            .onItemClickListener { onUrlClick(item.url) }
                            .addTo(this)
                    }
                    is LoadingVO -> {
                        LoadingEpoxyHolder_()
                            .id(item.id)
                            .item(item)
                            .addTo(this)
                    }
                    is ErrorVO -> {
                        ErrorEpoxyHolder_()
                            .id(item.id)
                            .item(item)
                            .onRetryListener(::onRetry)
                            .addTo(this)
                    }
                }
            }
        }
    }

    private fun onUrlClick(url: String) {
        viewModel.loadMore()
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(browserIntent)
    }

    private fun onRetry() {
        viewModel.load(1)
    }
}