package ru.sarmatin.mobble.mv.platform

import android.util.Log
import androidx.lifecycle.Observer
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.utils.failure.Failure


/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

abstract class MobbleStateFragment<S : MobbleStateViewModel.MobbleState> :
    MobbleAbstractFragment() {

    abstract val viewModel: MobbleStateViewModel<S>

    protected open val stateObserver: Observer<S> = Observer {

        handleFailure(it.failure)

        handleLoading(it.loading)

        handleState(it)
    }


    abstract fun handleState(state: S)

    abstract fun handleFailure(failure: Failure?)

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