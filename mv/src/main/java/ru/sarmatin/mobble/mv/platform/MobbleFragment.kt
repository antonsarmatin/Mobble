package ru.sarmatin.mobble.mv.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ru.sarmatin.mobble.mv.common.AbstractSpinnerLoadingFragment
import ru.sarmatin.mobble.mv.common.DefaultSpinnerLoadingFragment
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
abstract class MobbleFragment : Fragment() {

    //TODO GlobalState

    /**
     * Layout resourse Id that would be inflated
     */
    abstract fun layoutId(): Int

    abstract val viewModel: MobbleViewModel

    /**
     * Default failure observer for failure handling from MobbleViewModel
     * @see Failure
     * @see MobbleViewModel
     */
    protected open val failureObserver: Observer<Failure> = Observer { }

    /**
     * Default loading observer for loading handling from MobbleViewModel
     * @see AbstractSpinnerLoadingFragment
     * @see DefaultSpinnerLoadingFragment
     * @see MobbleViewModel
     */
    protected open val loadingObserver: Observer<Boolean> = Observer {
        if (it == true) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    /**
     * Is fragment created for first time?
     */
    protected fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.failure.observe(viewLifecycleOwner, failureObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)
    }

    override fun onPause() {
        viewModel.failure.removeObserver(failureObserver)
        viewModel.loading.removeObserver(loadingObserver)

        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        super.onPause()
    }

    private fun findSpinnerFragment(): AbstractSpinnerLoadingFragment? =
        parentFragmentManager.run {
            findFragmentByTag(AbstractSpinnerLoadingFragment.TAG_LOADING_FRAGMENT) as? AbstractSpinnerLoadingFragment
        }

    @Synchronized
    fun showLoading() {
        val spinnerFragment = findSpinnerFragment()
        if (spinnerFragment != null) return

        parentFragmentManager.run {
            //TODO IF PROVIDED CUSTOM LAYOUT

            val newSpinnerFragment = DefaultSpinnerLoadingFragment.newInstance()
            newSpinnerFragment.setTargetFragment(this@MobbleFragment, REQUEST_LOADING)
            newSpinnerFragment.show(this, AbstractSpinnerLoadingFragment.TAG_LOADING_FRAGMENT)
        }

    }

    fun hideLoading() = findSpinnerFragment()?.dismiss()

    companion object {

        private const val REQUEST_LOADING = 871

    }


}