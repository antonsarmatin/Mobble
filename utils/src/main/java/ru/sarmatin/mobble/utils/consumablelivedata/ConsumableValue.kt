package ru.sarmatin.mobble.utils.consumablelivedata

/**
 * Created by antonsarmatin
 * Date: 2020-02-14
 * Project: SavedViewModels
 */
class ConsumableValue<T>(private val data: T) {

    private var consumed = false

    fun consume(block: (T) -> Unit) {
        if (!consumed) {
            consumed = true
            block(data)
        }
    }

}
