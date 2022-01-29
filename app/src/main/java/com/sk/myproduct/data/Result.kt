package com.sk.myproduct.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val resultCode: String, val errorMessage: String) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$resultCode]"
            Loading -> "Loading"
            else -> ""
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null