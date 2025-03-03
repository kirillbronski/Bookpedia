package com.kbcoding.cmp.bookpedia.book.domain

import com.kbcoding.cmp.bookpedia.core.domain.DataError
import com.kbcoding.cmp.bookpedia.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): com.kbcoding.cmp.bookpedia.core.domain.Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): com.kbcoding.cmp.bookpedia.core.domain.Result<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}