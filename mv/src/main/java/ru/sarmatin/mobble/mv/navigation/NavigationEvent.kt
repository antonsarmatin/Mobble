package ru.sarmatin.mobble.mv.navigation

import androidx.lifecycle.LiveData

/**
 * Created by antonsarmatin
 * Date: 17/05/2020
 * Project: Mobble
 */
open class NavigationEvent : LiveData<NavAction> {

    constructor() : super()

    constructor(value: NavAction) : super(value)

}