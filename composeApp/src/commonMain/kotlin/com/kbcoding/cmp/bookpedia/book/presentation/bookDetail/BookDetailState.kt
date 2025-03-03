package com.kbcoding.cmp.bookpedia.book.presentation.bookDetail

import com.kbcoding.cmp.bookpedia.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
