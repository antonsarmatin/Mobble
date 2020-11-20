package ru.sarmatin.mobble.mv.platform

import androidx.annotation.LayoutRes
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
abstract class MobbleFragment(@LayoutRes layout: Int) : MobbleAbstractFragment(layout) {

    abstract override val viewModel: MobbleViewModel

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




    override fun onResume() {
        super.onResume()
        viewModel.failure.observe(viewLifecycleOwner, failureObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        viewModel.navigationEvent.observe(viewLifecycleOwner, navigationObserver)
    }

    override fun onPause() {
        viewModel.failure.removeObserver(failureObserver)
        viewModel.loading.removeObserver(loadingObserver)
        viewModel.navigationEvent.removeObserver(navigationObserver)
        super.onPause()
    }


}