package com.romanvolkov.worldnews.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romanvolkov.worldnews.IViewModel

class AppViewModelFactory(
    vararg _providers: Provider<*>
) : ViewModelProvider.Factory {

    companion object {
        inline fun <reified T : IViewModel> forInstance(noinline initializer: () -> T): AppViewModelFactory =
            AppViewModelFactory(
                Provider(
                    T::class.java,
                    initializer
                )
            )
    }

    private val providers: Map<Class<*>, Provider<*>> =
        _providers.associateBy({ it.constructableClass }, { it })

    override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
        @Suppress("UNCHECKED_CAST")
        return providers[modelClass]?.instance as? VM
            ?: throw IllegalArgumentException("Missing instance provider for ${modelClass.canonicalName}")
    }

    class Provider<T : IViewModel>(
        val constructableClass: Class<T>,
        private inline val initializer: () -> T
    ) {
        val instance: T by lazy { initializer() }
    }

}