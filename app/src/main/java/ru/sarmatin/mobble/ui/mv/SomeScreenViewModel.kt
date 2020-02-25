package ru.sarmatin.mobble.ui.mv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sarmatin.mobble.mv.platform.MobbleViewModel
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
class SomeScreenViewModel(handle: SavedStateHandle) : MobbleViewModel(handle) {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    init {
        _text.postValue("Some Text From VIEWMODEL")
    }

    fun produceError() {
        handleFailure(Failure.ServerError)
    }

    fun fetchData() {
        viewModelScope.launch {
            handleLoading(true)
            //Simulate network fetch
            withContext(Dispatchers.IO) {
                delay(3000)
                _text.postValue("New Data here")
            }
            handleLoading(false)
        }


    }
}