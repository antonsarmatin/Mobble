package ru.sarmatin.mobble.mv.navigation

import androidx.annotation.MainThread
import ru.sarmatin.mobble.utils.liveevent.EventLiveData

/**
 * Created by antonsarmatin
 * Date: 17/05/2020
 * Project: Mobble
 */
class MutableNavigationEvent : NavigationEvent {

    constructor() : super()

    constructor(value: NavAction?): super()

    @MainThread
    public override fun setValue(value: NavAction?) {
        observers.forEach { it.newValue() }
        super.setValue(value)
    }

    public override fun postValue(value: NavAction?) {
        observers.forEach { it.newValue() }
        super.postValue(value)
    }

}