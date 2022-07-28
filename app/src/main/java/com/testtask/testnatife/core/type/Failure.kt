package com.testtask.testnatife.core.type

sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()
    object UnknownError: Failure()
    object ListEmpty: Failure()
}
