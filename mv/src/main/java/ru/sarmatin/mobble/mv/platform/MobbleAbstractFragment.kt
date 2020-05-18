package ru.sarmatin.mobble.mv.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ru.sarmatin.mobble.mv.common.loading.DefaultFullscreen
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.common.loading.dialog.AbstractLoadingDialog
import ru.sarmatin.mobble.mv.common.loading.dialog.DefaultSpinnerLoadingDialog
import ru.sarmatin.mobble.mv.navigation.NavAction

/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

/**
 * Root Fragment class
 */
abstract class MobbleAbstractFragment : Fragment() {

    /**
     * Layout resourse Id that would be inflated
     */
    abstract fun layoutId(): Int

    /**
     * Navigation Event Observer
     * @see MobbleAbstractFragment.handleNavigationEvent
     */
    protected val navigationObserver: Observer<NavAction> = Observer {
        handleNavigationEvent(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    override fun onPause() {

        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        super.onPause()
    }

    /**
     * Is fragment created for first time?
     */
    protected fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    private fun findSpinnerFragment(): AbstractLoadingDialog? =
        parentFragmentManager.run {
            findFragmentByTag(AbstractLoadingDialog.TAG_LOADING_FRAGMENT) as? AbstractLoadingDialog
        }

    /**
     * Default loading dialog.
     * You can override this value in child ViewModel with your own AbstractLoadingDialog implementation to handle with loadingObserver
     * @see Loading
     * @see AbstractLoadingDialog
     * @see DefaultFullscreen
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

    @Synchronized
    fun showLoading(dialog: AbstractLoadingDialog?) {
        val spinnerFragment = findSpinnerFragment()
        if (spinnerFragment != null) return

        parentFragmentManager.run {

            dialog?.setTargetFragment(
                this@MobbleAbstractFragment,
                REQUEST_LOADING
            )
            dialog?.show(this, AbstractLoadingDialog.TAG_LOADING_FRAGMENT)
        }

    }

    fun hideLoading() = findSpinnerFragment()?.dismiss()

    /**
     * Override this function to implement navigation event handling
     * Function called by NavigationObserver
     *
     * @see navigationObserver
     * @see NavAction
     */
    protected open fun handleNavigationEvent(action: NavAction?){
        throw NotImplementedError(
            "handleNavigationevent(action: NavAction?) - You must override this function in order to be able handle navigation event"
        )
    }

    companion object {

        private const val REQUEST_LOADING = 871

    }

}