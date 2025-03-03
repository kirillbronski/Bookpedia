package com.kbcoding.cmp.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.kbcoding.cmp.bookpedia.book.data.database.FavoriteBookDao
import com.kbcoding.cmp.bookpedia.book.data.mappers.toBook
import com.kbcoding.cmp.bookpedia.book.data.mappers.toBookEntity
import com.kbcoding.cmp.bookpedia.book.data.network.RemoteBookDataSource
import com.kbcoding.cmp.bookpedia.book.domain.Book
import com.kbcoding.cmp.bookpedia.book.domain.BookRepository
import com.kbcoding.cmp.bookpedia.core.domain.DataError
import com.kbcoding.cmp.bookpedia.core.domain.EmptyResult
import com.kbcoding.cmp.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
): BookRepository {
    override suspend fun searchBooks(query: String): com.kbcoding.cmp.bookpedia.core.domain.Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): com.kbcoding.cmp.bookpedia.core.domain.Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            com.kbcoding.cmp.bookpedia.core.domain.Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            com.kbcoding.cmp.bookpedia.core.domain.Result.Success(Unit)
        } catch(e: SQLiteException) {
            com.kbcoding.cmp.bookpedia.core.domain.Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}