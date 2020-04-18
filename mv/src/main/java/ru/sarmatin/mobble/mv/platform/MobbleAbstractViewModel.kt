package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.ViewModel
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
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
    protected abstract fun handleFailure(failure: Failure)


}