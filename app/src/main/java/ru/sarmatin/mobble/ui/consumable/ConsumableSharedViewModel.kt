package ru.sarmatin.mobble.ui.consumable

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.sarmatin.mobble.utils.consumablelivedata.ConsumableLiveData
import ru.sarmatin.mobble.utils.consumablelivedata.ConsumableValue
import ru.sarmatin.mobble.utils.consumablelivedata.postValue

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