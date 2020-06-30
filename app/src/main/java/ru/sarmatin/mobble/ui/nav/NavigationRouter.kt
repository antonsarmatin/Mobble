package ru.sarmatin.mobble.ui.nav

import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.nav.router.Router
import ru.sarmatin.mobble.ui.nav.list.BookListViewModel
import timber.log.Timber

/**
 * Created by antonsarmatin
 * Date: 22/05/2020
 * Project: Mobble
 */
class NavigationRouter(private val navController: NavController) : Router {

    override fun onEvent(action: NavAction?) {
        Log.e(this.javaClass?.simpleName, "navAction: ${action?.javaClass?.simpleName}")
        when (action) {
            is BookListViewModel.ViewState.NavigateToBook -> {
                navController.navigate(
                    R.id.action_bookListFragment_to_bookDetailFragment,
                    bundleOf("bookId" to action.bookId)
                )
            }
            else -> {

            }
        }
    }

}