package com.andreich.weather.presentation.core

sealed interface UiMessage {

    class ShowMessage(val message: String) : UiMessage
}