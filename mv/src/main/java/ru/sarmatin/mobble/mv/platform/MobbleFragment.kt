package ru.sarmatin.mobble.mv.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.common.loading.dialog.AbstractLoadingDialog
import ru.sarmatin.mobble.mv.common.loading.dialog.DefaultSpinnerLoadingDialog
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
abstract class MobbleFragment : MobbleAbstractFragment() {

    abstract val viewModel: MobbleViewModel

    /**
     * Default failure observer for failure handling from MobbleViewModel
     * @see Failure
     * @see MobbleViewModel
     */
    protected open val failureObserver: Observer<Failure> = Observer {
        when (it) {
            is Failure.FeatureFailure -> throw NotImplementedError("You should override failureObserver to handle FeatureFailure")
        }
    }

    /**
     * Default loading observer for loading handling from MobbleViewModel
     * @see Loading
     * @see AbstractLoadingDialog
     * @see DefaultSpinnerLoadingDialog
     * @see MobbleViewModel
     * @see defaultLoadingDialog
     */
    protected open val loadingObserver: Observer<Loading> = Observer {
        when (it) {
            Loading.NoLoading -> {
                hideLoading()
            }
            is Loading.Fullscreen -> {
                when (it) {
                    is DefaultFullscreen -> {
                        showLoading(defaultLoadingDialog)
                    }
                    else -> showLoading(handleCustomLoading(it))
                }
            }
        }
    }

    /**
     * Default loading dialog.
     * You can override this value in child ViewModel with your own AbstractLoadingDialog implementation to handle with loadingObserver
     * @see Loading
     * @see AbstractLoadingDialog
     * @see MobbleViewModel.DefaultFullscreen
     * @see MobbleFragment.loadingObserver
     */
    protected open val defaultLoadingDialog: AbstractLoadingDialog =
        DefaultSpinnerLoadingDialog.newInstance()

    /**
     * You must override this function in order to be able handle your custom Loading states
     * If not overridden throws exception
     * @see Loading
     * @see loadingObserver
     */
    protected open fun handleCustomLoading(loading: Loading): AbstractLoadingDialog {
        throw NotImplementedError(
            "handleCustomLoading(loading: Loading) - " +
                    "You must override this function in order to be able handle your custom Loading states"
        )
    }


    override fun onResume() {
        super.onResume()
        viewModel.failure.observe(viewLifecycleOwner, failureObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)
    }

    override fun onPause() {
        viewModel.failure.removeObserver(failureObserver)
        viewModel.loading.removeObserver(loadingObserver)

        super.onPause()
    }


}