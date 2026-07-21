package com.andreich.weather.domain.model

sealed interface RequestResult {
    data object Success : RequestResult

    sealed interface Failure : RequestResult {

        data class NotFound(val message: String) : Failure

        data class InvalidApiKey(val message: String) : Failure

        data class InvalidParams(val message: String) : Failure

        data class TooManyRequests(val message: String) : Failure

        data class NoInternet(val message: String) : Failure

        data class TimeOut(val message: String) : Failure

        data class Server(val message: String) : Failure

        data class Unknown(val message: String) : Failure

        data class Serialization(val message: String) : Failure
    }
}