package ru.sarmatin.mobble.ui.nav

import android.os.Bundle
import androidx.navigation.findNavController
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.mv.platform.MobbleActivity
import ru.sarmatin.mobble.nav.common.NavHost
import ru.sarmatin.mobble.nav.common.Navigator
import ru.sarmatin.mobble.nav.router.Router

class NavigationActivity : MobbleActivity(), NavHost {

    override val router: Router by lazy { NavigationRouter(findNavController(R.id.nav_host_navigation_fragment)) }

    override val navigator = Navigator.init(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }

}
