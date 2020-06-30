package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.ViewModel
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.navigation.MutableNavigationEvent
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.mv.navigation.NavigationEvent
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

/**
 * Root ViewModel class
 */
abstract class MobbleAbstractViewModel : ViewModel() {

    /**
     * Field is used to handle navigation events
     * Consumes classes implemented NavAction interface
     * Observed by {@link MobbleAbstractFragment.navigationObserver}
     * You must override {@link MobbleAbstractFragment.handleNavigationEvent} to handle navigation event
     *
     * @see MobbleAbstractFragment.navigationObserver
     * @see MobbleAbstractFragment.handleNavigationEvent
     * @see NavAction
     */
    private val _navigationEvent = MutableNavigationEvent()
    val navigationEvent: NavigationEvent
        get() = _navigationEvent

    /**
     * Default fullscreen loading
     * You can override this property with custom Loading.Fullscreen child,
     * in this case your must override {@link MobbleFragment.handleCustomLoading} function
     * to handle custom Fullscreen loading
     * @see Loading.Fullscreen
     */
    protected open val defaultLoading: Loading.Fullscreen =
        DefaultFullscreen()


    /**
     * Handles Loading object
     * @see Loading
     */
    protected abstract fun handleLoading(loading: Loading)
    protected abstract fun handleLoading(isLoading: Boolean)


    /**
     * Handles Failure object
     */
    protected abstract fun handleFailure(failure: Failure, abortLoading: Boolean = true)

    protected fun handleNavigation(navAction: NavAction) {
        _navigationEvent.value = navAction
    }

}