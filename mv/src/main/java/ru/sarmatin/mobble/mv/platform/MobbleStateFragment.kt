package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.Observer
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.common.loading.dialog.AbstractLoadingDialog
import ru.sarmatin.mobble.mv.common.loading.dialog.DefaultSpinnerLoadingDialog
import ru.sarmatin.mobble.utils.failure.Failure


/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

abstract class MobbleStateFragment<S : MobbleStateViewModel.MobbleState> :
    MobbleAbstractFragment() {

    abstract val viewModel: MobbleStateViewModel<S>

    /**
     * Default State observer
     * Override this observer to implement your custom state handling
     *
     * @see MobbleStateViewModel.MobbleState
     * @see Failure
     * @see Loading
     *
     */
    protected open val stateObserver: Observer<S> = Observer {

        handleFailure(it.failure)

        handleLoading(it.loading)

        handleState(it)
    }


    /**
     * Handle custom state
     * Implement this function in your fragment
     */
    abstract fun handleState(state: S)

    /**
     * Handle Failure state
     * Implement this function to manage Failure state in your fragment or base fragment
     *
     * @see Failure
     *
     */
    abstract fun handleFailure(failure: Failure?)

    /**
     * Default loading handling function f
     * @see Loading
     * @see AbstractLoadingDialog
     * @see DefaultSpinnerLoadingDialog
     * @see MobbleStateViewModel
     * @see defaultLoadingDialog
     */
    protected open fun handleLoading(loading: Loading?){
        when (loading) {
            Loading.NoLoading -> {
                hideLoading()
            }
            is Loading.Fullscreen -> {
                when (loading) {
                    is DefaultFullscreen -> {
                        showLoading(defaultLoadingDialog)
                    }
                    else -> {
                        showLoading(handleCustomLoading(loading))
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.viewState.observe(viewLifecycleOwner, stateObserver)
    }

    override fun onPause() {
        viewModel.viewState.removeObserver(stateObserver)
        super.onPause()
    }

}