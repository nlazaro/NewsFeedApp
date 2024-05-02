package com.example.newsfeedapp.network

interface CallbackResponse<T> {
    fun onSuccess(model: T)

    fun onFailure(error: Throwable?)
}
