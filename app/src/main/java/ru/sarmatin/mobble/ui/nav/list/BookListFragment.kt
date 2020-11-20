package ru.sarmatin.mobble.ui.nav.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavHost
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_book_list.*
import kotlinx.android.synthetic.main.fragment_book_list.view.*
import kotlinx.android.synthetic.main.fragment_color.*
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.alert
import ru.sarmatin.mobble.model.BookModel
import ru.sarmatin.mobble.mv.common.loading.Loading
import ru.sarmatin.mobble.mv.platform.MobbleStateFragment
import ru.sarmatin.mobble.nav.platform.attachToNavigator
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 22/05/2020
 * Project: Mobble
 */
class BookListFragment : MobbleStateFragment<BookListViewModel.ViewState>(R.layout.fragment_book_list),
    BookListAdapter.ItemClickListener {

    override val viewModel: BookListViewModel by viewModels()

    private lateinit var adapter: BookListAdapter

    private val listObserver = Observer<List<BookModel>> {
        adapter.setData(it)
    }

    override fun handleLoading(loading: Loading?) {
        when(loading){
            Loading.NoLoading -> {
                refreshLayout.isRefreshing = false
            }
            is Loading.Fullscreen -> {
                refreshLayout.isRefreshing = true
            }
        }
    }

    override fun handleFeatureState(featureState: BookListViewModel.ViewState?) {
        Log.e(this.javaClass?.simpleName,"featureState: ${featureState?.javaClass?.simpleName}" )
        textEmpty.isVisible = false
        viewModel.books.removeObserver(listObserver)
        //handle states here
        when (featureState) {
            BookListViewModel.ViewState.Empty -> {
                textEmpty.isVisible = true
            }
            BookListViewModel.ViewState.BookList -> {
                viewModel.books.observe(viewLifecycleOwner, listObserver)
            }
            null -> {

            }
        }
    }

    override fun handleFailure(failure: Failure?) {
        if(failure is BookListViewModel.RefreshFailure) alert(getString(R.string.failure_refresh))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachToNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set views and proceed list click

        adapter = BookListAdapter().apply {
            this.setListener(this@BookListFragment)
        }

        with(view.rvBook) {
            this.adapter = this@BookListFragment.adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        refreshLayout.setOnRefreshListener {
            viewModel.refreshList()
        }

    }

    override fun onItemClicked(id: String) {
        viewModel.bookClicked(id)
    }

}