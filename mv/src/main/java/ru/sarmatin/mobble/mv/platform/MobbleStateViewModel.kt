package ru.sarmatin.mobble.mv.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.utils.failure.Failure
import java.io.Serializable


/**
 * Created by antonsarmatin
 * Date: 2020-04-16
 * Project: Mobble
 */

abstract class MobbleStateViewModel<S : MobbleStateViewModel.MobbleState>(handle: SavedStateHandle) :
    MobbleAbstractViewModel() {


    protected val _viewState = handle.getLiveData<S>("viewState")
    val viewState: LiveData<S>
        get() = _viewState

    /**
     * Common State class for ViewModel
     * Extend this class to add custom state related fields
     *
     * WARNING! State should not contain bulk data!
     *
     */

    open class MobbleState : Serializable {

        val failure: Failure? = null

        val loading: Loading? = null

    }

    open class MobbleAction()


    override fun handleFailure(failure: Failure) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun handleLoading(loading: Loading) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}