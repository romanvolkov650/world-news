package com.romanvolkov.worldnews.news.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.romanvolkov.worldnews.GlideApp
import com.romanvolkov.worldnews.KotlinEpoxyHolder
import com.romanvolkov.worldnews.R
import com.romanvolkov.worldnews.news.NewsItem
import java.text.SimpleDateFormat
import java.util.*

@EpoxyModelClass(layout = R.layout.vh_news)
abstract class NewsItemEpoxyHolder : EpoxyModelWithHolder<NewsItemEpoxyHolder.Holder>() {

    @EpoxyAttribute
    lateinit var item: NewsItem

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onItemClickListener: (url: String) -> Unit

    override fun bind(holder: Holder) {
        holder.view.setOnClickListener { onItemClickListener(item.url) }
        GlideApp
            .with(holder.image)
            .load(item.imageUrl)
            .placeholder(R.drawable.noimg)
            .into(holder.image)
        holder.title.text = item.title.getString(holder.title.context)
        holder.description.text = item.description.getString(holder.title.context)
        holder.source.text = item.source.getString(holder.title.context)
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val output = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
        val d = sdf.parse(item.publishedAt)
        val formattedTime: String = output.format(d!!)
        holder.time.text = formattedTime
    }

    class Holder : KotlinEpoxyHolder() {
        val view by bind<View>(R.id.news_item)
        val image by bind<ImageView>(R.id.iv_news)
        val title by bind<TextView>(R.id.tv_title)
        val description by bind<TextView>(R.id.tv_description)
        val source by bind<TextView>(R.id.tv_author)
        val time by bind<TextView>(R.id.tv_time)
    }

}