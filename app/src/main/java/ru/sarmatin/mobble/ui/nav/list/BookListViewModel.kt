package ru.sarmatin.mobble.ui.nav.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sarmatin.mobble.model.BookModel
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.mv.platform.state.FeatureState
import ru.sarmatin.mobble.source.BookSource.getBooksList
import ru.sarmatin.mobble.utils.failure.Failure


/**
 * Created by antonsarmatin
 * Date: 22/05/2020
 * Project: Mobble
 */
class BookListViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<BookListViewModel.ViewState>(handle) {

    override val defaultFeatureState: ViewState
        get() = ViewState.Empty

    private val _books = handle.getLiveData<MutableList<BookModel>>("books")
    val books: LiveData<List<BookModel>>
        get() = Transformations.map(_books) { it.toList() }

    sealed class ViewState() : FeatureState() {
        object Empty : ViewState()
        object BookList : ViewState()
        data class NavigateToBook(val bookId: String) : ViewState(), NavAction
    }

    init {
        viewModelScope.launch {
            handleLoading(true)
            delay(2000)
            _books.postValue(getBooksList())
            handleLoading(false)
            updateFeatureState(ViewState.BookList)
        }
    }

    fun bookClicked(id: String) {
        updateFeatureState(ViewState.NavigateToBook(id))
    }

    fun refreshList() {
        viewModelScope.launch {
            handleLoading(true)
            delay(2000)
            handleFailure(RefreshFailure())
        }
    }

    class RefreshFailure : Failure.FeatureFailure()

}