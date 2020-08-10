package com.romanvolkov.worldnews

import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

data class ErrorVO(val id: String, val message: StringVO) : IViewObject

@EpoxyModelClass(layout = R.layout.vh_error)
abstract class ErrorEpoxyHolder : EpoxyModelWithHolder<ErrorEpoxyHolder.Holder>() {

    @EpoxyAttribute
    lateinit var item: ErrorVO

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onRetryListener: () -> Unit

    override fun bind(holder: Holder) {
        holder.message.text = item.message.getString(holder.message.context)
        holder.retryBtn.setOnClickListener { onRetryListener() }
    }

    class Holder : KotlinEpoxyHolder() {
        val retryBtn by bind<Button>(R.id.retryBtn)
        val message: TextView by bind(R.id.message)
    }
}