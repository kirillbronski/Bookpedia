package com.kbcoding.cmp.bookpedia.book.presentation.bookDetail

import com.kbcoding.cmp.bookpedia.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}