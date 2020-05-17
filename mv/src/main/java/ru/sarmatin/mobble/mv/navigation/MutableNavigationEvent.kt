package ru.sarmatin.mobble.mv.navigation

import ru.sarmatin.mobble.utils.liveevent.EventLiveData

/**
 * Created by antonsarmatin
 * Date: 17/05/2020
 * Project: Mobble
 */
class MutableNavigationEvent : NavigationEvent {

    constructor() : super()

    constructor(value: NavAction?): super()

    public override fun postValue(value: NavAction?) {
        super.postValue(value)
    }

    public override fun setValue(value: NavAction?) {
        super.setValue(value)
    }

}