package ru.sarmatin.mobble.ui.nav.list

import android.service.autofill.TextValueSanitizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_book_list.view.*
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.model.BookModel
import java.text.ParsePosition

/**
 * Created by antonsarmatin
 * Date: 20/06/2020
 * Project: Mobble
 */
class BookListAdapter : RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    private val data = arrayListOf<BookModel>()

    private var listener: ItemClickListener? = null

    interface ItemClickListener {

        fun onItemClicked(id: String)

    }

    fun setData(data: List<BookModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_book_list, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(position)
    }

    inner class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cover: ImageView = itemView.imgCover
        private val title: TextView = itemView.textTitle
        private val author: TextView = itemView.textAuthor

        fun bind(position: Int) {
            val book = data[position]

            Glide.with(cover).load(book.resIdCover)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(32, 0)))
                .into(cover)
            title.text = book.title
            author.text = book.author

            itemView.setOnClickListener {
                listener?.onItemClicked(book.id)
            }

        }


    }

}