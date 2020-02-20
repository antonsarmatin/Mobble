package ru.sarmatin.mobble.ui.consumable

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.sarmatin.consumablelivedata.ConsumableLiveData
import ru.sarmatin.consumablelivedata.ConsumableValue
import ru.sarmatin.consumablelivedata.postValue

/**
 * Created by antonsarmatin
 * Date: 2020-02-20
 * Project: Mobble
 */
class ConsumableSharedViewModel : ViewModel() {

    private val _data = ConsumableLiveData<String>()
    val data: LiveData<ConsumableValue<String>>
        get() = _data

    fun setData(data: String) {
        _data.postValue(data)
    }

}