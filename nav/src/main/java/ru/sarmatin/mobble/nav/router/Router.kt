package ru.sarmatin.mobble.nav.router

import ru.sarmatin.mobble.mv.navigation.NavAction

/**
 * Created by antonsarmatin
 * Date: 20/05/2020
 * Project: Mobble
 */
interface Router {

    fun onEvent(action: NavAction?)

}