package ru.sarmatin.mobble.mv.platform

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.common.loading.dialog.AbstractLoadingDialog
import ru.sarmatin.mobble.mv.common.loading.dialog.DefaultSpinnerLoadingDialog
import ru.sarmatin.mobble.mv.platform.state.FeatureState
import ru.sarmatin.mobble.utils.failure.Failure


/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

abstract class MobbleStateFragment<S : FeatureState>(@LayoutRes layout: Int) :
    MobbleAbstractFragment(layout) {

    abstract override val viewModel: MobbleStateViewModel<S>

    private var currentStateFeatureState: S? = null

    private var restoreStateAfterPause = false

    /**
     * Default State observer
     * Override this observer to implement your custom state handling
     *
     * @see MobbleStateViewModel.MobbleState
     * @see Failure
     * @see Loading
     *
     */
    protected open val stateObserver: Observer<MobbleStateViewModel.MobbleState<S>> = Observer {
        handleCommonState(it.commonState)
        if (compareFeatureState(it.featureState))
            handleFeatureState(it.featureState)
        else if (restoreStateAfterPause) {
            handleFeatureState(it.featureState)
            restoreStateAfterPause = false
        }
    }

    protected open fun handleCommonState(commonState: MobbleStateViewModel.CommonState) {
        handleFailure(commonState.failure)
        handleLoading(commonState.loading)
    }

    /**
     * Handle custom state
     * Implement this function in your fragment
     */
    abstract fun handleFeatureState(featureState: S?)

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
    protected open fun handleLoading(loading: Loading?) {
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

    private fun compareFeatureState(newFeatureState: S?): Boolean {
        return if (currentStateFeatureState != newFeatureState) {
            currentStateFeatureState = newFeatureState
            true
        } else
            false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, stateObserver)
    }

    override fun onResume() {
        super.onResume()
//        viewModel.viewState.observe(viewLifecycleOwner, stateObserver)
        viewModel.navigationEvent.observe(viewLifecycleOwner, navigationObserver)
    }

    override fun onPause() {
//        viewModel.viewState.removeObserver(stateObserver)
        viewModel.navigationEvent.removeObserver(navigationObserver)
        restoreStateAfterPause = true
        super.onPause()
    }

}