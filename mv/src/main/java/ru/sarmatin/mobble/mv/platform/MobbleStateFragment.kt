package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.Observer


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

    protected open val stateObserver: Observer<out MobbleStateViewModel.MobbleState> = Observer {
        TODO("not implemented")
    }



}