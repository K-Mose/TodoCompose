package com.mose.kim.todocompose.util

// sharedViewModel에서 데이터 로딩 성공 시에만 화면이 표시되도록
sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}