package com.andreich.weather.network

import com.andreich.weather.domain.model.RequestResult
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

suspend inline fun<T> safeApiCall(
    crossinline apiCall: suspend () -> T,
    crossinline onSuccess: suspend (T) -> Unit): RequestResult {
    return try {
        val data = apiCall()
        onSuccess(data)
        RequestResult.Success
    } catch (e: HttpRequestTimeoutException) {
        RequestResult.Failure.TimeOut(e.message.orEmpty())
    } catch (e: ConnectTimeoutException) {
        RequestResult.Failure.TimeOut(e.message.orEmpty())
    } catch (e: ClientRequestException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> {
                RequestResult.Failure.InvalidApiKey(e.message)
            }

            HttpStatusCode.BadRequest -> {
                RequestResult.Failure.InvalidParams(e.message)
            }

            HttpStatusCode.NotFound -> {
                RequestResult.Failure.NotFound(e.message)
            }

            HttpStatusCode.TooManyRequests -> {
                RequestResult.Failure.TooManyRequests(e.message)
            }

            else -> {
                RequestResult.Failure.Unknown(e.message)
            }
        }
    } catch (e: ServerResponseException) {
        RequestResult.Failure.Server(e.message)
    } catch (e: IOException) {
        RequestResult.Failure.NoInternet(e.message.orEmpty())
    } catch (e: SerializationException) {
        RequestResult.Failure.Serialization(e.message.orEmpty())
    } catch (e: Exception) {
        RequestResult.Failure.Unknown(e.message.orEmpty())
    }
}