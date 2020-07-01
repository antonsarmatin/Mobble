package ru.sarmatin.mobble.model

/**
 * Created by antonsarmatin
 * Date: 22/05/2020
 * Project: Mobble
 */
data class BookModel(
    val id: String,
    val title: String,
    val author: String,
    val resIdCover: Int?,
    val description: String
)