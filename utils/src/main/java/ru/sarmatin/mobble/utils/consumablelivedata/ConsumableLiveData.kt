package ru.sarmatin.mobble.utils.consumablelivedata

import androidx.lifecycle.MutableLiveData

/**
 * Created by antonsarmatin
 * Date: 2020-02-14
 * Project: SavedViewModels
 */
class ConsumableLiveData<T> : MutableLiveData<ConsumableValue<T>>()

fun <T> ConsumableLiveData<T>.setValue(value: T) {
    this.value = ConsumableValue(value)
}

fun <T> ConsumableLiveData<T>.postValue(value: T) {
    this.postValue(
        ConsumableValue(
            value
        )
    )
}