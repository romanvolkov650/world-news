package com.romanvolkov.worldnews

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

data class LoadingVO(val id: String) : IViewObject

@EpoxyModelClass(layout = R.layout.vh_loading)
abstract class LoadingEpoxyHolder : EpoxyModelWithHolder<LoadingEpoxyHolder.Holder>() {

    @EpoxyAttribute
    lateinit var item: LoadingVO

    override fun bind(holder: Holder) {
    }

    class Holder : KotlinEpoxyHolder() {
    }
}