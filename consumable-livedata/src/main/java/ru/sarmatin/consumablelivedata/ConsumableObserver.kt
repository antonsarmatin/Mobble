package ru.sarmatin.consumablelivedata

import androidx.lifecycle.Observer

/**
 * Created by antonsarmatin
 * Date: 2020-02-14
 * Project: SavedViewModels
 */
//class ConsumableObserver<T>(private val observer: Observer<in ConsumableValue<T>>): Observer<ConsumableValue<T>> {
//
//    override fun onChanged(t: ConsumableValue<T>?) {
//        observer.onChanged(t)
//    }
//}

class ConsumableObserver<T>(private val block: (T) -> Unit): Observer<ConsumableValue<T>> {

    override fun onChanged(t: ConsumableValue<T>?) {
        t?.consume(block)
    }

}