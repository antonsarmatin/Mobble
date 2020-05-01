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

abstract class MobbleStateFragment : MobbleAbstractFragment() {

    abstract val viewModel: MobbleStateViewModel<*>

    //TODO DEFAULT STATE HANDLING
    // Loading, Failure
    // Possibility to implement custom handling?

    protected open val stateObserver: Observer<in MobbleStateViewModel.MobbleState> = Observer {

        when (it.failure) {
            Failure.NetworkConnection -> {
                TODO()
            }
            Failure.ServerError -> {
                TODO()
            }
            Failure.Ignore -> {
                TODO()
            }
            is Failure.FeatureFailure -> {
                TODO()
            }
            null -> {

            }
        }

        when (it.loading) {
            Loading.NoLoading -> {
                hideLoading()
            }
            is Loading.Fullscreen -> {
                when (it.loading) {
                    is DefaultFullscreen -> {
                        showLoading(defaultLoadingDialog)
                    }
                    else -> {
                        showLoading(handleCustomLoading(it.loading as Loading.Fullscreen))
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