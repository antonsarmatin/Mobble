package ru.sarmatin.mobble.source

import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.model.BookModel
import java.util.*

/**
 * Created by antonsarmatin
 * Date: 20/06/2020
 * Project: Mobble
 */
object BookSource {
    //Don't do like that, okay?
    private val bookList by lazy {
        val books = arrayListOf<BookModel>()

        val bookArch = BookModel(
            id = UUID.randomUUID().mostSignificantBits.toString(),
            title = "Чистая архитектура",
            author = "Роберт Мартин",
            resIdCover = R.drawable.cover_arch,
            description = "\"Идеальный программист\" и \"Чистый код\" - легендарные бестселлеры Роберта Мартина - рассказывают, как достичь высот профессионализма. \"Чистая архитектура\" продолжает эту тему, но не предлагает несколько вариантов в стиле \"решай сам\", а объясняет, что именно следует делать, по какой причине и почему именно такое решение станет принципиально важным для вашего успеха.\n" +
                    "\n" +
                    "Роберт Мартин дает прямые и лаконичные ответы на ключевые вопросы архитектуры и дизайна. \"Чистую архитектуру\" обязаны прочитать разработчики всех уровней, системные аналитики, архитекторы и каждый программист, который желает подняться по карьерной лестнице или хотя бы повлиять на людей, которые занимаются данной работой.\n" +
                    "Все архитектуры подчиняются одним и тем же правилам!\n" +
                    "Роберт Мартин (дядюшка Боб)"

        )

        val bookPatterns = BookModel(
            id = UUID.randomUUID().mostSignificantBits.toString(),
            title = "ПАТТЕРНЫ ПРОЕКТИРОВАНИЯ",
            author = "Александр Швец",
            resIdCover = R.drawable.cover_patterns,
            description = "Книга «Погружение в Паттерны Проектирования» описывает 22 классических паттерна проектирования, а также 8 принципов проектирования, на которых они основаны."

        )

        val bookKotlin = BookModel(
            id = UUID.randomUUID().mostSignificantBits.toString(),
            title = "Kotlin в действии",
            author = "Жемеров Дмитрий, Исакова Светлана",
            resIdCover = R.drawable.cover_kotlin,
            description = "Язык Kotlin предлагает выразительный синтаксис, мощную и понятную систему типов, великолепную поддержку и бесшовную совместимость с существующим кодом на Java, богатый выбор библиотек и фреймворков. Kotlin может компилироваться в байт-код Java, поэтому его можно использовать везде, где используется Java, включая Android. А благодаря эффективному компилятору и маленькой стандартной библиотеке, Kotlin практически не привносит накладных расходов."
        )
        books.addAll(listOf(bookArch, bookPatterns, bookKotlin))

        books
    }

    fun getBooksList() = bookList

    fun getBook(id: String) = bookList.find { it.id == id }

}