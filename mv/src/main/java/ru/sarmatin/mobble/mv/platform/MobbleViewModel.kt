package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.common.loading.Loading
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
abstract class MobbleViewModel(handle: SavedStateHandle) : MobbleAbstractViewModel() {

    private val _failure = EventLiveData<Failure>()
    val failure: LiveData<Failure>
        get() = _failure

    private val _loading = handle.getLiveData<Loading>("loading")
    val loading: LiveData<Loading>
        get() = _loading

    /**
     * Handles Failure object and modify failure state of ViewModel
     */
    override fun handleFailure(failure: Failure, abortLoading: Boolean) {
        handleLoading(Loading.NoLoading, false)
        _failure.postValue(failure)
        if (abortLoading)
            _loading.postValue(Loading.NoLoading)
    }

    /**
     * Handles Loading object and modify loading state of ViewModel
     * @see Loading
     */
    override fun handleLoading(loading: Loading, abortError: Boolean) {
        if (abortError) _failure.postValue(null)
        _loading.postValue(loading)
    }

    /**
     * Handles loading state with defaultLoading
     * when True - post defaultLoading
     * when False - post NoLoading
     * @see Loading
     * @see defaultLoading
     */
    override fun handleLoading(isLoading: Boolean, abortError: Boolean) {
        val loading = if (isLoading) defaultLoading as Loading else Loading.NoLoading
        handleLoading(loading, abortError)
    }


}