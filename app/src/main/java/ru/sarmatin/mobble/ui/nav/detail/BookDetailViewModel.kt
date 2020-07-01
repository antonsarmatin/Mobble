package ru.sarmatin.mobble.ui.nav.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sarmatin.mobble.model.BookModel
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.mv.platform.state.FeatureState
import ru.sarmatin.mobble.source.BookSource.getBook

/**
 * Created by antonsarmatin
 * Date: 22/05/2020
 * Project: Mobble
 */
class BookDetailViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<BookDetailViewModel.ViewState>(handle) {

    override val defaultFeatureState: ViewState
        get() = ViewState.Empty

    sealed class ViewState : FeatureState() {
        object Empty : ViewState()
        data class Book(val bookModel: BookModel) : ViewState()
    }

    fun fetchBook(id: String?) {
        id?.let { bookId ->
            val book = getBook(bookId)
            book?.let {
                updateFeatureState(ViewState.Book(it))
            }
        }
    }

}