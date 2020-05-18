package ru.sarmatin.mobble.utils.consumablelivedata

import androidx.lifecycle.Observer

/**
 * Created by antonsarmatin
 * Date: 2020-02-14
 * Project: SavedViewModels
 */
open class ConsumableObserver<T>(private val block: (T) -> Unit): Observer<ConsumableValue<T>> {

    override fun onChanged(t: ConsumableValue<T>?) {
        t?.consume(block)
    }

}