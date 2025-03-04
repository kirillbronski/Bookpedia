package com.kbcoding.cmp.bookpedia.book.presentation.bookList

import com.kbcoding.cmp.bookpedia.book.domain.Book
import com.kbcoding.cmp.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)