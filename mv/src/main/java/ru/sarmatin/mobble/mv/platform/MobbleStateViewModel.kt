package ru.sarmatin.mobble.mv.platform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.utils.failure.Failure
import java.io.Serializable


/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

abstract class MobbleStateViewModel<S : MobbleStateViewModel.MobbleState>(handle: SavedStateHandle) :
    MobbleAbstractViewModel() {


    protected val _viewState = handle.getLiveData<S>("viewState")
    val viewState: LiveData<S>
        get() = _viewState

    init {
        if (_viewState.value == null)
            _viewState.value = defaultState
    }

    /**
     * Default VM state
     */
    abstract val defaultState: S

    /**
     * Common State class for ViewModel
     * Extend this class to add custom state related fields
     *
     * WARNING! State should not contain bulk data!
     *
     */

    open class MobbleState(
    ) : Serializable {

        internal var _loading: Loading? = null
        val loading: Loading?
            get() = _loading

        internal var _failure: Failure? = null
        val failure: Failure?
            get() = _failure


    }

    private open class MobbleAction()


    /**
     * Handles Failure object and modify failure state of ViewModel State
     */
    override fun handleFailure(failure: Failure, abortLoading: Boolean) {
        updateState(viewState.value?.apply {
            _failure = failure
            if (abortLoading)
                _loading = Loading.NoLoading

        })
    }

    /**
     * Handles Loading object and modify loading state of ViewModel State
     * @see Loading
     */
    override fun handleLoading(loading: Loading) {
        updateState(viewState.value?.apply {
            _loading = loading
        })
    }


    /**
     * Handles loading state with defaultLoading
     * when True - post defaultLoading
     * when False - post NoLoading
     * @see Loading
     * @see defaultLoading
     */
    override fun handleLoading(isLoading: Boolean) {
        val currentState = viewState.value
        updateState(currentState?.apply {
            this._loading = if (isLoading) defaultLoading else Loading.NoLoading
        })
    }

    protected fun updateState(newState: S?) {
        newState?.let {
            _viewState.postValue(it)
        }
    }

}