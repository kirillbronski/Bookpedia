package com.kbcoding.cmp.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kbcoding.cmp.bookpedia.book.data.database.DatabaseFactory
import com.kbcoding.cmp.bookpedia.book.data.database.FavoriteBookDatabase
import com.kbcoding.cmp.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.kbcoding.cmp.bookpedia.book.data.network.RemoteBookDataSource
import com.kbcoding.cmp.bookpedia.book.data.repository.DefaultBookRepository
import com.kbcoding.cmp.bookpedia.book.domain.BookRepository
import com.kbcoding.cmp.bookpedia.book.presentation.SelectedBookViewModel
import com.kbcoding.cmp.bookpedia.book.presentation.bookDetail.BookDetailViewModel
import com.kbcoding.cmp.bookpedia.book.presentation.bookList.BookListViewModel
import com.kbcoding.cmp.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}