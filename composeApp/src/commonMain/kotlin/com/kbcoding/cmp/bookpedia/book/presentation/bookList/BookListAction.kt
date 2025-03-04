package com.kbcoding.cmp.bookpedia.book.presentation.bookList

import com.kbcoding.cmp.bookpedia.book.domain.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String): BookListAction
    data class OnBookClick(val book: Book): BookListAction
    data class OnTabSelected(val index: Int): BookListAction
}