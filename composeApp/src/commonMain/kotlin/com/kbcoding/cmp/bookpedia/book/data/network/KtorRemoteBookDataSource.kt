package com.kbcoding.cmp.bookpedia.book.data.network

import com.kbcoding.cmp.bookpedia.book.data.dto.BookWorkDto
import com.kbcoding.cmp.bookpedia.book.data.dto.SearchResponseDto
import com.kbcoding.cmp.bookpedia.core.data.safeCall
import com.kbcoding.cmp.bookpedia.core.domain.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): com.kbcoding.cmp.bookpedia.core.domain.Result<SearchResponseDto, DataError.Remote> {
        return safeCall <SearchResponseDto> {
            httpClient.get(
                            urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): com.kbcoding.cmp.bookpedia.core.domain.Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }
}