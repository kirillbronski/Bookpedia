package com.kbcoding.cmp.bookpedia.book.data.network

import com.kbcoding.cmp.bookpedia.book.data.dto.BookWorkDto
import com.kbcoding.cmp.bookpedia.book.data.dto.SearchResponseDto
import com.kbcoding.cmp.bookpedia.core.domain.DataError

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): com.kbcoding.cmp.bookpedia.core.domain.Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): com.kbcoding.cmp.bookpedia.core.domain.Result<BookWorkDto, DataError.Remote>
}