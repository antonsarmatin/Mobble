package ru.sarmatin.mobble.ui.nav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.nav.platform.MobbleNavActivity
import ru.sarmatin.mobble.nav.router.Router

class NavigationActivity : MobbleNavActivity() {

    override fun getRouter() = NavigationRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)



    }

}
