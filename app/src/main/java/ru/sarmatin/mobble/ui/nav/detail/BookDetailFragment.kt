package ru.sarmatin.mobble.ui.nav.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_book_detail.*
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.model.BookModel
import ru.sarmatin.mobble.mv.platform.MobbleStateFragment
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 22/05/2020
 * Project: Mobble
 */
class BookDetailFragment : MobbleStateFragment<BookDetailViewModel.ViewState>(R.layout.fragment_book_detail) {

    override val viewModel: BookDetailViewModel by viewModels()

    override fun handleFeatureState(featureState: BookDetailViewModel.ViewState?) {
        Log.e(this.javaClass?.simpleName,"featureState: ${featureState?.javaClass?.simpleName}" )
        when(featureState){
            BookDetailViewModel.ViewState.Empty -> {

            }
            is BookDetailViewModel.ViewState.Book -> {
                showBook(featureState.bookModel)
            }
        }
    }

    override fun handleFailure(failure: Failure?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("bookId")?.let {
            viewModel.fetchBook(it)
        }

    }

    private fun showBook(bookModel: BookModel){
        Glide.with(imgCover).load(bookModel.resIdCover)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(48, 0)))
            .into(imgCover)

        textTitle.text =  bookModel.title
        textAuthor.text = bookModel.author
        textDesc.text = bookModel.description
    }

}