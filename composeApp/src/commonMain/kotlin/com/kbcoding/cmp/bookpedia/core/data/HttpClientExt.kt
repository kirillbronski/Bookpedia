package com.kbcoding.cmp.bookpedia.core.data

import com.kbcoding.cmp.bookpedia.core.domain.DataError
import com.kbcoding.cmp.bookpedia.core.domain.Result.*
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): com.kbcoding.cmp.bookpedia.core.domain.Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: UnresolvedAddressException) {
        return Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): com.kbcoding.cmp.bookpedia.core.domain.Result<T, DataError.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Error(DataError.Remote.SERVER)
        else -> Error(DataError.Remote.UNKNOWN)
    }
}