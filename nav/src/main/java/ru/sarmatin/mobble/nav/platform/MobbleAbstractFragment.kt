package ru.sarmatin.mobble.nav.platform

import ru.sarmatin.mobble.mv.platform.MobbleAbstractFragment
import ru.sarmatin.mobble.mv.platform.MobbleActivity
import ru.sarmatin.mobble.mv.platform.MobbleStateFragment
import ru.sarmatin.mobble.mv.platform.state.FeatureState
import ru.sarmatin.mobble.nav.common.NavHost
import ru.sarmatin.mobble.nav.common.Navigator
import ru.sarmatin.mobble.nav.router.Router

/**
 * Created by antonsarmatin
 * Date: 20/06/2020
 * Project: Mobble
 */

/**
 * Find a [Router] of a Fragment and its containing
 * [MobbleActivity].
 *
 * Calling this on a Activity that is not a [NavHost] or within a [NavHost]
 * will result in an [IllegalStateException]
 */
fun MobbleAbstractFragment.findRouter(): Router {
    if (activity is NavHost) {
        return (activity as NavHost).router
    } else throw IllegalStateException("${activity?.javaClass?.simpleName} is not NavHost")
}

/**
 * Find a [Navigator] of a Fragment and its containing
 * [MobbleActivity].
 *
 * Calling this on a Activity that is not a [NavHost] or within a [NavHost]
 * will result in an [IllegalStateException]
 */
fun MobbleAbstractFragment.findNavigator(): Navigator {
    if (activity is NavHost) {
        return (activity as NavHost).navigator
    } else throw IllegalStateException("${activity?.javaClass?.simpleName} is not NavHost")
}

/**
 * Attach [MobbleStateFragment] as NavigationEventOwner to [Navigator] of
 * [MobbleActivity] as [NavHost].
 *
 * Calling this on a Activity that is not a [NavHost] or within a [NavHost]
 * will result in an [IllegalStateException]
 */
fun MobbleAbstractFragment.attachToNavigator() {
    if (activity is NavHost) {
        this.findNavigator().attachNavigationEventOwner(viewModel)
        this.lifecycle.addObserver(findNavigator())

    } else throw IllegalStateException("${activity?.javaClass?.simpleName} is not NavHost")
}