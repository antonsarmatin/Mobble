package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.mv.platform.state.FeatureState
import ru.sarmatin.mobble.utils.failure.Failure
import java.io.Serializable


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
     */
    private val _viewState = MutableLiveData<MobbleState<S>>()
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
        _viewState.value = MobbleState(defaultCommonState, defaultFeatureState)
    }

    /**
     * State class for ViewModel
     * @param commonState
     * @param featureState
     *
     * WARNING! State should not contain bulk data!
     *
     */
    data class MobbleState<S : FeatureState>(val commonState: CommonState, val featureState: S?) :
        Serializable {

        fun withLoading(loading: Loading, abortError: Boolean = true) =
            if (abortError) {
                this.copy(
                    commonState = commonState.copy(
                        loading = loading,
                        failure = null
//                       failure = Failure.Ignore
                    )
                )
            } else {
                this.copy(
                    commonState = commonState.copy(
                        loading = loading
                    )
                )
            }

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
        _viewState.value = newState
    }

    /**
     * Handles Loading object and modify loading state of ViewModel State
     * @see Loading
     */
    override fun handleLoading(loading: Loading, abortError: Boolean) {
        val newState = _viewState.value?.withLoading(loading)
        _viewState.value = newState
    }


    /**
     * Handles loading state with defaultLoading
     * when True - post defaultLoading
     * when False - post NoLoading
     * @see Loading
     * @see defaultLoading
     */
    override fun handleLoading(isLoading: Boolean, abortError: Boolean) {
        if (isLoading) {
            handleLoading(defaultLoading, abortError)
        } else {
            handleLoading(Loading.NoLoading, abortError)
        }
    }

    /**
     * Update state
     */
    protected fun updateFeatureState(state: S?, abortError: Boolean = false) {
        if (state is NavAction) {
            handleNavigation(state)
            return
        }


        val newState = if (abortError) {
            _viewState.value?.copy(featureState = state)?.withFailure(null)
        } else {
            _viewState.value?.copy(featureState = state)
        }
        _viewState.value = newState
    }

}