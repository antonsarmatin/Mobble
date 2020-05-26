package ru.sarmatin.mobble.nav.platform

import android.app.Activity
import ru.sarmatin.mobble.mv.platform.MobbleActivity
import ru.sarmatin.mobble.nav.common.NavHost
import ru.sarmatin.mobble.nav.router.Router

/**
 * Created by antonsarmatin
 * Date: 25/05/2020
 * Project: Mobble
 */
/**
 * Find a [Router] of a [MobbleActivity].
 *
 * Calling this on a Activity that is not a [NavHost] or within a [NavHost]
 * will result in an [IllegalStateException]
 */
fun MobbleActivity.findRouter(): Router {
    if (this is NavHost) return this.router
    else throw IllegalStateException("${this.javaClass.simpleName} is not NavHost")
}