package com.romanvolkov.worldnews.news.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val layoutManager = (newsEpoxy.layoutManager as LinearLayoutManager)
        var lastItem = 0
        var visibleItemCount = 0
        var totalItemCount = 0
        newsEpoxy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount;
                    totalItemCount = layoutManager.itemCount;
                    lastItem = layoutManager.findFirstVisibleItemPosition();
                }
                if ((visibleItemCount + lastItem) >= totalItemCount) {
                    viewModel.loadMore()
                }


            }
        })

        viewModel.state.observe(this, Observer(::updateItems))
    }

    private fun updateItems(items: List<IValueObject>) {
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
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun onRetry() {
        viewModel.load(1)
    }
}