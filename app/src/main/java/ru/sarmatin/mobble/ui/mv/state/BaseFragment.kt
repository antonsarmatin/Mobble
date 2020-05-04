package ru.sarmatin.mobble.ui.mv.state

import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.alert
import ru.sarmatin.mobble.mv.platform.MobbleStateFragment
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 2020-05-03
 * Project: Mobble
 */
abstract class BaseFragment<S : MobbleStateViewModel.MobbleState> : MobbleStateFragment<S>() {


    override fun handleFailure(failure: Failure?) {
        //Handle failure here
        if(failure!=null){
            alert(getString(R.string.error_message))
        }
    }
}