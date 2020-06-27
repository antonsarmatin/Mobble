package ru.sarmatin.mobble.nav.common

import android.util.Log
import androidx.lifecycle.*
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.mv.navigation.NavigationEvent
import ru.sarmatin.mobble.mv.platform.MobbleAbstractViewModel
import ru.sarmatin.mobble.mv.platform.MobbleActivity
import ru.sarmatin.mobble.nav.platform.MobbleNavActivity
import ru.sarmatin.mobble.nav.platform.MobbleNavApplication
import ru.sarmatin.mobble.nav.router.Router
import javax.net.ssl.HostnameVerifier

/**
 * Created by antonsarmatin
 * Date: 20/05/2020
 * Project: Mobble
 */
class Navigator private constructor(
    private val lifecycleOwner: LifecycleOwner,
    private var host: NavHost
) : LifecycleObserver {


    private var navigationEvent: NavigationEvent? = null

    private var navigationObserver: Observer<NavAction> = Observer {
        onAction(it)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        navigationEvent?.observe(lifecycleOwner, navigationObserver)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        navigationEvent?.removeObserver(navigationObserver)
    }


    fun attachNavigationEventOwner(mobbleAbstractViewModel: MobbleAbstractViewModel) {
        navigationEvent = mobbleAbstractViewModel.navigationEvent
    }

    private fun onAction(navAction: NavAction){
        host.router.onEvent(navAction)
    }

    companion object {

        fun init(lifecycleOwner: LifecycleOwner, host: NavHost) = Navigator(lifecycleOwner, host)

    }

}