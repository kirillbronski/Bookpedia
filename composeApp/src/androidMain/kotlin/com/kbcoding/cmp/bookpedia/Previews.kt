package com.kbcoding.cmp.bookpedia

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kbcoding.cmp.bookpedia.book.domain.Book
import com.kbcoding.cmp.bookpedia.book.presentation.bookList.BookListScreen
import com.kbcoding.cmp.bookpedia.book.presentation.bookList.BookListState
import com.kbcoding.cmp.bookpedia.book.presentation.bookList.components.BookSearchBar

@Preview(showBackground = true)
@Composable
private fun BookSearchBarPreview() {
    BookSearchBar(
        searchQuery = "",
        onSearchQueryChange = {},
        onImeSearch = {},
        modifier = Modifier
            .fillMaxWidth()
    )
}

val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("Kirill Bronski"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.67854,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}

@Preview(showSystemUi = true)
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = books,
            isLoading = false
        ),
        onAction = {}
    )
}