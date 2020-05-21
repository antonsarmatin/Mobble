package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.mv.platform.state.FeatureState
import ru.sarmatin.mobble.utils.failure.Failure


/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

@Suppress("NAME_SHADOWING")
abstract class MobbleStateViewModel<S : FeatureState>(handle: SavedStateHandle) :
    MobbleAbstractViewModel() {

    /**
     * State LiveData field
     * @see MobbleState
     * @see SavedStateHandle
     */
    private val _viewState = handle.getLiveData<MobbleState<S>>("viewState")
    val viewState: LiveData<MobbleState<S>>
        get() = _viewState

    fun getState() = _viewState.value
    fun getCommonState() = _viewState.value?.commonState
    fun getFeatureState() = _viewState.value?.featureState

    /**
     * Default common VM state
     */
    protected val defaultCommonState = CommonState(Loading.NoLoading, null)

    /**
     * Default feature VM state
     */
    abstract val defaultFeatureState: S

    /**
     * Init with default state
     */
    init {
        _viewState.postValue(MobbleState(defaultCommonState, defaultFeatureState))
    }

    /**
     * State class for ViewModel
     * @param commonState
     * @param featureState
     *
     * WARNING! State should not contain bulk data!
     *
     */
    data class MobbleState<S : FeatureState>(val commonState: CommonState, val featureState: S?) {

        fun withLoading(loading: Loading) = this.copy(
            commonState = commonState.copy(
                loading = loading
            )
        )

        fun withFailure(failure: Failure?, abortLoading: Boolean = true) =
            if (abortLoading) {
                this.copy(
                    commonState = commonState.copy(
                        loading = Loading.NoLoading,
                        failure = failure
                    )
                )
            } else {
                this.copy(
                    commonState = commonState.copy(
                        failure = failure
                    )
                )
            }

    }

    data class CommonState(val loading: Loading?, val failure: Failure?)

    private open class MobbleAction()


    /**
     * Handles Failure object and modify failure state of ViewModel State
     */
    override fun handleFailure(failure: Failure, abortLoading: Boolean) {
        val newState = _viewState.value?.withFailure(failure, abortLoading)
        _viewState.postValue(newState)
    }

    /**
     * Handles Loading object and modify loading state of ViewModel State
     * @see Loading
     */
    override fun handleLoading(loading: Loading) {
        val newState = _viewState.value?.withLoading(loading)
        _viewState.postValue(newState)
    }


    /**
     * Handles loading state with defaultLoading
     * when True - post defaultLoading
     * when False - post NoLoading
     * @see Loading
     * @see defaultLoading
     */
    override fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            handleLoading(defaultLoading)
        } else {
            handleLoading(Loading.NoLoading)
        }
    }

    /**
     * Update state
     */
    protected fun updateFeatureState(state: S?) {
        if (state is NavAction) handleNavigation(state)

        val newState = _viewState.value?.copy(featureState = state)
        _viewState.postValue(newState)
    }

}