package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.sarmatin.mobble.utils.failure.Failure
import ru.sarmatin.mobble.utils.liveevent.EventLiveData

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 *
 * @see Failure
 *
 */
abstract class MobbleViewModel(handle: SavedStateHandle) : ViewModel() {

    private val _failure = EventLiveData<Failure>()
    val failure: LiveData<Failure>
        get() = _failure

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    protected fun handleFailure(failure: Failure) {
        handleLoading(false)
        _failure.postValue(failure)
    }

    protected fun handleLoading(isLoading: Boolean) {
        _loading.postValue(isLoading)
    }

}