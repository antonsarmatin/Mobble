package ru.sarmatin.mobble.nav.common

import ru.sarmatin.mobble.nav.router.Router

/**
 * Created by antonsarmatin
 * Date: 21/05/2020
 * Project: Mobble
 */
interface NavHost {

   val router: Router

   val navigator: Navigator

}