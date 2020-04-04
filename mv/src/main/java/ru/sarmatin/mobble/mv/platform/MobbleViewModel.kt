package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
abstract class MobbleViewModel(handle: SavedStateHandle) : ViewModel() {

    private val _failure = EventLiveData<Failure>()
    val failure: LiveData<Failure>
        get() = _failure

    private val _loading = handle.getLiveData<Loading>("loading")
    val loading: LiveData<Loading>
        get() = _loading

    /**
     * Default fullscreen loading
     * @see Loading.Fullscreen
     */
    protected open val defaultLoading: Loading.Fullscreen = DefaultFullscreen()


    protected open fun handleFailure(failure: Failure) {
        handleLoading(Loading.NoLoading)
        _failure.postValue(failure)
    }

    /**
     * Handles Loading object a
     * @see Loading
     */
    protected open fun handleLoading(loading: Loading) {
        _loading.postValue(loading)
    }

    /**
     * Handles loading state with Boolean param
     * when True - post defaultLoading
     * when False - post NoLoading
     * @see Loading
     * @see defaultLoading
     */
    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) _loading.postValue(defaultLoading) else _loading.postValue(Loading.NoLoading)
    }


    class DefaultFullscreen : Loading.Fullscreen()

}