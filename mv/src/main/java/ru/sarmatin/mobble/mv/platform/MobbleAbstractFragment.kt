package ru.sarmatin.mobble.mv.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import ru.sarmatin.mobble.mv.common.loading.dialog.AbstractLoadingDialog

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

    companion object {

        private const val REQUEST_LOADING = 871

    }

}